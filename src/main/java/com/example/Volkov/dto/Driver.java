package com.example.Volkov.dto;

import lombok.Data;
import lombok.NonNull;


@Data
public class Driver {
    @NonNull private final int driverId;
    @NonNull private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        return driverId == driver.driverId;
    }

}
