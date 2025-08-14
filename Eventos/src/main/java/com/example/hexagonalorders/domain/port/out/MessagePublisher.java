package com.example.hexagonalorders.domain.port.out;

public interface MessagePublisher {
    void publish(String topic, String payload);
}
