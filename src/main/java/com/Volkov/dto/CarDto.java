package com.Volkov.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Well, it's a car.")
public class CarDto {
    private String registrationNumber;
    private String model;
    private String color;
    @ApiModelProperty(notes = "Is this car insured? You can't add it to Driver without insurance.")
    private boolean insurance;
    private DriverDto owner;
}
