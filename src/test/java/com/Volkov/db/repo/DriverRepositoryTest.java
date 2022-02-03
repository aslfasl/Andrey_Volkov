package com.Volkov.db.repo;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.exceptions.InsuranceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepositoryTest;

    @AfterEach
    void tearDown(){
        driverRepositoryTest.deleteAll();
    }

    @Test
    void itShouldFindDriverEntityByCarRegistrationNumber() throws InsuranceException {
        DriverEntity driver = new DriverEntity("Ivan", LocalDate.of(1999, 9, 11));
        CarEntity car = new CarEntity("aaa", "opel", "green", true);
        driver.addNewCar(car);
        driverRepositoryTest.save(driver);
        
        DriverEntity requestedDriver = driverRepositoryTest.findDriverEntityByCarRegistrationNumber("aaa");

        assertEquals(driver.getDriverId(), requestedDriver.getDriverId());
        assertTrue(driver.getName().equalsIgnoreCase(requestedDriver.getName()));
        assertEquals(driver.getBirthDate(), requestedDriver.getBirthDate());
    }

    @Test
    void itShouldReturnDriverByIdWithHisCars() throws InsuranceException {
        DriverEntity driver = new DriverEntity("Ivan", LocalDate.of(1999, 9, 11));
        CarEntity car = new CarEntity("aaa", "opel", "green", true);
        driver.addNewCar(car);
        driverRepositoryTest.save(driver);

        DriverEntity requestedDriver = driverRepositoryTest.findDriverWithCarsById(driver.getDriverId());

        assertEquals(driver.getName(), requestedDriver.getName());
        assertEquals(driver.getBirthDate(), requestedDriver.getBirthDate());
        assertEquals(driver.getCars().get(0), requestedDriver.getCars().get(0));
        assertEquals(driver, requestedDriver);
    }

    @Test
    void itShouldGetAllDriversWithInitializedCars() throws InsuranceException {
        DriverEntity driver = new DriverEntity("Ivan", LocalDate.of(1999, 9, 11));
        CarEntity car = new CarEntity("aaa", "opel", "green", true);
        driver.addNewCar(car);
        driverRepositoryTest.save(driver);

        List<DriverEntity> myCars = driverRepositoryTest.findAllDriversWithCars();

        assertEquals(1, myCars.size());
    }
}