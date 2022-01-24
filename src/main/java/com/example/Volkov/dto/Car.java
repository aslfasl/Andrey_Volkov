package com.example.Volkov.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

@Data
public class Car {
    @NonNull
    @Getter
    private final String carId;
    @NonNull
    private String model;
    @NonNull
    private String color;
    @NonNull
    private boolean insurance;
    private int ownerId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return carId.equals(car.carId);
    }
}
