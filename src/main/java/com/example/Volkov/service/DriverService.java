package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.WrongAgeException;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    @Getter
    private List<Driver> allDrivers = new ArrayList<>();


    public void addDriver(Driver newDriver) throws WrongAgeException {
        if (!allDrivers.contains(newDriver) && ValidationService.driverAgeCheck(newDriver.getDateOfBirth())) {
            allDrivers.add(newDriver);
        }
    }

    public void addNewDriver(int driverId, String name, LocalDate birthDate) throws WrongAgeException {
        Driver newDriver = new Driver(driverId, name, birthDate);
        if (ValidationService.driverAgeCheck(birthDate)){
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
