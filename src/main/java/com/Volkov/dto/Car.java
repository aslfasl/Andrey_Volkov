package com.Volkov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String regNumber;
    private String model;
    private String color;
    private boolean insurance;
}
