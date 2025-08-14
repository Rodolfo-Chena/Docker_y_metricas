package com.example.hexagonalorders.application.service;

import com.example.hexagonalorders.domain.event.DomainEvent;
import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.OutboxMessage;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.port.in.OrderUseCase;
import com.example.hexagonalorders.domain.port.out.OrderNumberGenerator;
import com.example.hexagonalorders.domain.port.out.OrderRepository;
import com.example.hexagonalorders.domain.port.out.OutboxRepository;
import com.example.hexagonalorders.domain.service.OrderValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;
    private final OrderNumberGenerator orderNumberGenerator;
    private final OrderValidationService orderValidationService;
    private final ApplicationEventPublisher eventPublisher;
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        // Validar
        orderValidationService.validateOrder(order);

        // Generar número de orden
        OrderNumber orderNumber = orderNumberGenerator.generate();
        Order orderWithNumber = new Order(
                orderNumber,
                order.getCustomerId(),
                order.getOrderDate(),
                order.getItems(),
                order.getStatus()
        );

        // ---- CAPTURAR eventos ANTES de guardar ----
        List<DomainEvent> events = new ArrayList<>(orderWithNumber.getDomainEvents());

        // Guardar la orden
        Order savedOrder = orderRepository.save(orderWithNumber);

        // Publicar eventos de dominio y persistir en outbox (desde la lista capturada)
        for (DomainEvent event : events) {
            eventPublisher.publishEvent(event); // Publicación interna (opcional)
          //  persistToOutbox(event, "Order", orderNumber.value());
        }

        // Limpiar eventos del aggregate (original)
        orderWithNumber.clearDomainEvents();

        return savedOrder;
    }

    @Override
    public Optional<Order> getOrder(OrderNumber orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOrder(OrderNumber orderNumber) {
        orderRepository.deleteByOrderNumber(orderNumber);
    }

    /**
     * Confirma una orden (PENDING -> CONFIRMED), emite OrderConfirmedEvent,
     * publica eventos y los persiste en la tabla outbox.
     */
    @Override
    @Transactional
    public Order confirmOrder(OrderNumber orderNumber) {
        // 1) Buscar la orden por su número
        Optional<Order> orderOpt = orderRepository.findByOrderNumber(orderNumber);
        if (orderOpt.isEmpty()) {
            // Si tienes una excepción tipada (OrderNotFoundException), úsala aquí
            throw new RuntimeException("Order not found: " + orderNumber.value());
        }

        Order order = orderOpt.get();

        // 2) Confirmar en el aggregate (emite OrderConfirmedEvent)
        // Como el aggregate no tiene id propio, enviamos null (igual que en OrderCreatedEvent)
        order.confirm(null);

        // ---- CAPTURAR eventos ANTES de guardar ----
        List<DomainEvent> events = new ArrayList<>(order.getDomainEvents());

        // 3) Guardar cambios
        Order savedOrder = orderRepository.save(order);

        // 4) Publicar y persistir en Outbox todos los DomainEvents generados (desde la lista capturada)
        for (DomainEvent event : events) {
            eventPublisher.publishEvent(event); // Publicación interna (opcional)
           // persistToOutbox(event, "Order", orderNumber.value());
        }

        // 5) Limpiar eventos del aggregate
        order.clearDomainEvents();

        return savedOrder;
    }

    protected void persistToOutbox(DomainEvent event, String aggregateType, String aggregateId) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            String eventType = event.getClass().getSimpleName();
            UUID uuid = UUID.nameUUIDFromBytes(aggregateId.getBytes());

            OutboxMessage message = OutboxMessage.createPendingMessage(
                    aggregateType,
                    uuid,
                    eventType,
                    payload
            );
            outboxRepository.save(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to persist event to outbox", e);
        }
    }
}
