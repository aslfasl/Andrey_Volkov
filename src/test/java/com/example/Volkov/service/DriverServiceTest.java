package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import com.example.Volkov.exceptions.WrongAgeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceTest {

    @Autowired
    DriverService driverService;

    @BeforeEach
    void setUp() {
        Driver testDriver1 = new Driver(1, "First Driver", LocalDate.of(2000, 1, 2));
        Driver testDriver2 = new Driver(
                2, "Second Driver", LocalDate.of(2000, 12, 31));

        Car testCar1 = new Car("a1", "opel", "green", true);
        Car testCar2 = new Car("b2", "lada", "black", true);
        Car testCar3 = new Car("c3", "bmw", "white", true);
        Car testCar4 = new Car("d4", "jeep", "yellow", true);

        try {
            driverService.addDriver(testDriver1);
            driverService.addDriver(testDriver2);
        } catch (WrongAgeException e) {
            e.printStackTrace();
        }

        testDriver1.addNewCar(testCar1);
        testDriver1.addNewCar(testCar2);
        testDriver2.addNewCar(testCar3);
        testDriver2.addNewCar(testCar4);
    }

    @Test
    void canAddDriver() throws WrongAgeException {
        Driver driver = new Driver(11, "ElevenTest Driver", LocalDate.of(2002, 2, 3));
        assertFalse(driverService.getAllDrivers().contains(driver));
        driverService.addDriver(driver);
        assertTrue(driverService.getAllDrivers().contains(driver));
    }

    @Test
    void addNewDriver() throws WrongAgeException {
        int driverId = 12;
        String name = "add1";
        LocalDate birthDate = LocalDate.of(1969, 2, 12);
        Driver driver = new Driver(driverId, name, birthDate);

        assertFalse(driverService.getAllDrivers().contains(driver));
        driverService.addNewDriver(driverId, name, birthDate);
        assertTrue(driverService.getAllDrivers().contains(driver));
    }

    @Test
    void canGetDriverById() throws ObjectNotFoundException {
        int driverId = 2;
        Driver driver = driverService.getDriverById(driverId);
        assertEquals(driverId, driver.getDriverId());
    }

    @Test
    void canDeleteDriverById() throws ObjectNotFoundException {
        Driver driver = driverService
                .getAllDrivers()
                .stream()
                .findAny()
                .get();

        assertTrue(driverService.getAllDrivers().contains(driver));
        driverService.deleteDriverById(driver.getDriverId());
        assertFalse(driverService.getAllDrivers().contains(driver));
    }

    @Test
    void canUpdateDriverById() throws ObjectNotFoundException {
        int driverId = 2;
        String newName = "NewDriverName";
        LocalDate newBirthDate = LocalDate.of(1965, 3, 20);
        Driver driver = driverService.getDriverById(driverId);

        driverService.updateDriverById(driverId, newName, newBirthDate);

        assertEquals(driverId, driver.getDriverId());
        assertEquals(newName, driver.getName());
        assertEquals(newBirthDate, driver.getDateOfBirth());
    }

    @Test
    void canGetDriverByCarId() throws ObjectNotFoundException {
        String carId = "d4";
        Driver driver = driverService.getDriverByCarId(carId);
        assertEquals(carId, driver.getCarList()
                .stream()
                .filter(car -> car.getCarId().equalsIgnoreCase(carId))
                .findAny()
                .get()
                .getCarId());
    }

    @Test
    void canAddCarToDriverByDriverId() throws ObjectNotFoundException {
        int driverId = 1;
        Car car = new Car("zxc", "bmw", "blue", true);

        Driver driver = driverService.getDriverById(driverId);
        assertFalse(driver.getCarList().contains(car));
        driverService.addCarToDriverByDriverId(car, driverId);
        assertTrue(driver.getCarList().contains(car));
    }
}