package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.InsuranceException;
import com.example.Volkov.exceptions.WrongAgeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {

    @Test
    void shouldCheckInsurance() throws InsuranceException {
        Car car = new Car("qwerty", "renault", "white", true);
        assertTrue(ValidationService.insuranceCheck(car));
    }

    @Test
    void shouldThrowInsuranceException() {
        Car car = new Car("qwerty", "renault", "white", false);
        assertThrows(InsuranceException.class, () -> {
            ValidationService.insuranceCheck(car);
        });
    }

    @Test
    void shouldCheckDriversAge() throws WrongAgeException {
        Driver driver = new Driver(55, "James", LocalDate.of(1970, 6,17));
        assertTrue(ValidationService.driverAgeCheck(driver.getDateOfBirth()));
    }

    @Test
    void shouldThrowWrongAgeException() {
        Driver driver = new Driver(55, "James", LocalDate.of(970, 6,17));
        assertThrows(WrongAgeException.class, () -> {
            ValidationService.driverAgeCheck(driver.getDateOfBirth());
        });
    }
}