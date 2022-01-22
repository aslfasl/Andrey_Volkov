package com.example.Volkov.dao;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DriversRepository {

    @Getter
    private List<Driver> allDrivers = new ArrayList<>();


    public void addDriver(Driver newDriver) {
        if (!allDrivers.contains(newDriver)) {
            allDrivers.add(newDriver);
        }
    }

    public Driver getDriverById(int driverId) throws ObjectNotFoundException {
        for (Driver driver : allDrivers) {
            if (driver.getDriverId() == driverId) {
                return driver;
            }
        }
        throw new ObjectNotFoundException("Driver not found");
    }

    public void deleteDriverById(int driverId) throws ObjectNotFoundException {
        allDrivers.remove(getDriverById(driverId));
    }

    public void updateDriverById(int driverId, String newName, LocalDate newBirthDate) throws ObjectNotFoundException {
        Driver driver = getDriverById(driverId);
        if (newName != null) {
            driver.setName(newName);
        }
        if (newBirthDate != null) {
            driver.setDateOfBirth(newBirthDate);
        }
    }

    public Driver getDriverByCarId(String carId) throws ObjectNotFoundException {
        for (Driver driver : allDrivers) {
            for (Car car : driver.getCarList()) {
                if (car.getCarId().equalsIgnoreCase(carId)) {
                    return driver;
                }
            }
        }
        throw new ObjectNotFoundException("Driver not found");
    }

    public void addCarToDriverByDriverId(Car car, int driverId) throws ObjectNotFoundException {
        Driver driver = getDriverById(driverId);
        if (!driver.getCarList().contains(car)) {
            driver.addNewCar(car);
        }
    }
}
