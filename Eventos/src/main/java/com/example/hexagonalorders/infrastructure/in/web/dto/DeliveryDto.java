package com.example.hexagonalorders.infrastructure.in.web.dto;

import java.time.LocalDateTime;

public class DeliveryDto {

    public String deliveryId;
    public String routeId;
    public String deliveryPersonId;
    public String orderNumber;
    public String status;
    public LocalDateTime scheduledAt;
    public AddressDto destination;

    public static class AddressDto {
        public String street;
        public String city;
        public String postalCode;
    }
}
