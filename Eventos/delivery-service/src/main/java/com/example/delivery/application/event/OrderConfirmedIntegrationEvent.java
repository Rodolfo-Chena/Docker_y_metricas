package com.example.delivery.application.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class OrderConfirmedIntegrationEvent {
  private String orderNumber;
  private String eventType;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime confirmedAt;

  public OrderConfirmedIntegrationEvent() {}

  public String getOrderNumber() { return orderNumber; }
  public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

  public String getEventType() { return eventType; }
  public void setEventType(String eventType) { this.eventType = eventType; }

  public LocalDateTime getConfirmedAt() { return confirmedAt; }
  public void setConfirmedAt(LocalDateTime confirmedAt) { this.confirmedAt = confirmedAt; }
}
