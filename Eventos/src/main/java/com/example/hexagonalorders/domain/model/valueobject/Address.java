package com.example.hexagonalorders.domain.model.valueobject;

import java.util.Objects;

public class Address {

    private final String street;
    private final String city;
    private final String postalCode;

    public Address(String street, String city, String postalCode) {
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("La calle no puede estar vacía");
        }
        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("La ciudad no puede estar vacía");
        }
        if (postalCode == null || postalCode.isEmpty()) {
            throw new IllegalArgumentException("El código postal no puede estar vacío");
        }

        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getFullAddress() {
        return street + ", " + city + ", CP " + postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address other = (Address) o;
        return Objects.equals(street, other.street) &&
               Objects.equals(city, other.city) &&
               Objects.equals(postalCode, other.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, postalCode);
    }
}
