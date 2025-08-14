package com.example.hexagonalorders.infrastructure.in.web;

import com.example.hexagonalorders.domain.model.Order;
import com.example.hexagonalorders.domain.model.valueobject.OrderNumber;
import com.example.hexagonalorders.domain.port.in.OrderUseCase;
import com.example.hexagonalorders.infrastructure.in.web.dto.OrderDto;
import com.example.hexagonalorders.infrastructure.in.web.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for order operations.
 * This is an input adapter in the infrastructure layer that handles HTTP requests
 * and delegates to the application core through the OrderUseCase port.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management API")
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final OrderMapper orderMapper;

    @Operation(summary = "Create a new order", description = "Creates a new order and returns the created order.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderMapper.toDomain(orderDto);
        Order savedOrder = orderUseCase.createOrder(order);
        return ResponseEntity.ok(orderMapper.toDto(savedOrder));
    }

    @Operation(summary = "Get an order by order number", description = "Retrieves an order by its order number.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String orderNumber) {
        return orderUseCase.getOrder(new OrderNumber(orderNumber))
                .map(orderMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an order by order number", description = "Deletes an order by its order number.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Order deleted successfully")
    })
    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderNumber) {
        orderUseCase.deleteOrder(new OrderNumber(orderNumber));
        return ResponseEntity.noContent().build();
    }

    //  Confirmar Order 
    @Operation(
        summary = "Confirm an order",
        description = "Confirms an order, changing its status from PENDING to CONFIRMED."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Order confirmed successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Order cannot be confirmed (e.g., not in PENDING status)")
    })
    @PostMapping("/{orderNumber}/confirm")
    public ResponseEntity<OrderDto> confirmOrder(@PathVariable String orderNumber) {
        try {
            Order confirmed = orderUseCase.confirmOrder(new OrderNumber(orderNumber));
            return ResponseEntity.ok(orderMapper.toDto(confirmed));
        } catch (IllegalStateException ex) {
            // Estado inválido para confirmar (PENDING)
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            // Si el servicio lanza una excepción de "no encontrado"
            String msg = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (msg.contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }
    
}
