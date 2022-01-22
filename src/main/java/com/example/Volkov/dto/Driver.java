package com.example.Volkov.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
public class Driver {
    @NonNull private final int driverId;
    @NonNull private String name;
    @NonNull private LocalDate dateOfBirth;
    private List<Car> carList = new ArrayList<>();

    public void addNewCar(Car car) {
        if (!carList.contains(car)){
            carList.add(car);
            car.setOwnerId(driverId);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        return driverId == driver.driverId;
    }

}
