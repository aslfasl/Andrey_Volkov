package com.Volkov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private String registrationNumber;
    private String model;
    private String color;
    private boolean insurance;
    private DriverDto owner;
}
