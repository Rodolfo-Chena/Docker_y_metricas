package com.example.hexagonalorders.infrastructure.in.web.dto;

import java.time.LocalDateTime;

public class CreateDeliveryRequest {

    public String routeId;
    public String deliveryPersonId;
    public String orderNumber;
    public AddressDto destination;
    public LocalDateTime scheduledAt;

    public static class AddressDto {
        public String street;
        public String city;
        public String postalCode;
    }
}
