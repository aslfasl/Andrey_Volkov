package com.volkov.db.entity;

import com.volkov.exceptions.InsuranceException;
import com.volkov.exceptions.ObjectAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DriverEntityTest {

    @Test
    void shouldAddNewCar() throws InsuranceException, ObjectAlreadyExistsException {
        DriverEntity driver = new DriverEntity("Test", LocalDate.of(2000, 1, 1));
        CarEntity car = new CarEntity("aa2", "toyota", "blue", true);

        driver.addNewCar(car);

        assertEquals(driver.getCars().get(0), car);
    }

    @Test
    void shouldThrowInsuranceExceptionWhenAddCar() {
        DriverEntity driver = new DriverEntity("Test", LocalDate.of(2000, 1, 1));
        CarEntity car = new CarEntity("aa2", "toyota", "blue", false);

        InsuranceException e = assertThrows(InsuranceException.class, () -> driver.addNewCar(car));

        assertEquals("Can not add car without insurance", e.getMessage());
    }

    @Test
    void shouldThrowObjectAlreadyExistsExceptionWhenAddCar() {
        DriverEntity driver = new DriverEntity("Test", LocalDate.of(2000, 1, 1));
        CarEntity car = new CarEntity("aa2", "toyota", "blue", true);
        driver.getCars().add(car);

        ObjectAlreadyExistsException e = assertThrows(ObjectAlreadyExistsException.class, () -> driver.addNewCar(car));

        assertEquals("That driver already has this car", e.getMessage());
    }
}