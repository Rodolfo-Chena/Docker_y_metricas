package com.example.hexagonalorders.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public class RouteId {

    private final String value;

    public RouteId() {
        this.value = UUID.randomUUID().toString();
    }

    public RouteId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteId)) return false;
        RouteId other = (RouteId) o;
        return Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
