package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.db.repo.DriverRepository;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.exceptions.WrongAgeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceTest {

    @Autowired
    DriverService driverServiceTest;

    @Autowired
    DriverRepository driverRepositoryTest;

    @AfterEach
    void tearDown() {
        driverRepositoryTest.deleteAll();
    }

    ////    @BeforeEach
////    void setUp() {
////        DriverEntity testDriver1Entity = new DriverEntity(1, "First Driver", LocalDate.of(2000, 1, 2));
////        DriverEntity testDriver2Entity = new DriverEntity(
////                2, "Second Driver", LocalDate.of(2000, 12, 31));
////
////        CarEntity testCar1Entity = new CarEntity("a1", "opel", "green", true);
////        CarEntity testCar2Entity = new CarEntity("b2", "lada", "black", true);
////        CarEntity testCar3Entity = new CarEntity("c3", "bmw", "white", true);
////        CarEntity testCar4Entity = new CarEntity("d4", "jeep", "yellow", true);
////
////        try {
////            driverService.addDriver(testDriver1Entity);
////            driverService.addDriver(testDriver2Entity);
////        } catch (WrongAgeException e) {
////            e.printStackTrace();
////        }
////
////        testDriver1Entity.addNewCar(testCar1Entity);
////        testDriver1Entity.addNewCar(testCar2Entity);
////        testDriver2Entity.addNewCar(testCar3Entity);
////        testDriver2Entity.addNewCar(testCar4Entity);
////    }
//
    @Test
    void shouldAddNewDriverWhenOneIsGiven() throws WrongAgeException {
        String name = "TestDriver";
        LocalDate birthdate = LocalDate.of(2002, 2, 3);
        DriverEntity driver = new DriverEntity(name, birthdate);

        assertFalse(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
        driverServiceTest.addDriver(driver);
        assertTrue(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
    }

    @Test
    void shouldThrowWrongAgeException() {
        String name = "TestDriver";
        LocalDate birthdate = LocalDate.of(1002, 2, 3);
        DriverEntity driver = new DriverEntity(name, birthdate);

        WrongAgeException exception = assertThrows(WrongAgeException.class, () ->
                driverServiceTest.addDriver(driver));

        assertEquals("This age is not allowed", exception.getMessage());
    }

    @Test
    void shouldTakeParametersAndAddNewDriver() throws WrongAgeException {
        String name = "add1";
        LocalDate birthdate = LocalDate.of(1969, 2, 12);

        assertFalse(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
        driverServiceTest.addNewDriver(name, birthdate);
        assertTrue(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
    }

    @Test
    void shouldGetDriverById() throws ObjectNotFoundException, InsuranceException {
        DriverEntity driver = new DriverEntity("driverTest", LocalDate.of(1995, 5, 5));
        CarEntity car = new CarEntity("aaa", "opel", "white", true);
        driver.addNewCar(car);
        driverRepositoryTest.save(driver);
        int driverId = driver.getDriverId();

        DriverEntity driverFromDatabase = driverServiceTest.getDriverById(driverId);

        assertEquals(driver.getName(), driverFromDatabase.getName());
        assertEquals(driver, driverFromDatabase);
    }

    @Test
    void shouldThrowObjectNotFoundExceptionOnGetDriverById() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> driverServiceTest.getDriverById(1));

        assertEquals("Driver not found", exception.getMessage());
    }

    @Test
    void shouldDeleteDriverById() {
        DriverEntity driver =
                new DriverEntity("testName", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driver);

        assertTrue(driverRepositoryTest.existsDriverByNameAndBirthDate(driver.getName(), driver.getBirthDate()));
        driverServiceTest.deleteDriverById(driver.getDriverId());
        assertFalse(driverRepositoryTest.existsDriverByNameAndBirthDate(driver.getName(), driver.getBirthDate()));
    }


    @Test
    void shouldUpdateDriverByIdWithAFollowingParameters() throws ObjectNotFoundException {
        String newName = "NewDriverName";
        LocalDate newBirthDate = LocalDate.of(1965, 3, 20);
        DriverEntity driverEntity = new DriverEntity(newName, newBirthDate);
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();

        driverServiceTest.updateDriverById(driverId, newName, newBirthDate);

        assertEquals(driverId, driverEntity.getDriverId());
        assertEquals(newName, driverEntity.getName());
        assertEquals(newBirthDate, driverEntity.getBirthDate());
    }

    @Test
    void shouldThrowObjectNotFoundException() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () ->
                driverServiceTest.updateDriverById(1, "testName",
                        LocalDate.of(1998, 10, 10)));

        assertEquals("Driver not found", exception.getMessage());
    }

    @Test
    void shouldGetDriverByCarRegistrationNumber() throws ObjectNotFoundException, InsuranceException {
        String regNumber = "d4";
        DriverEntity driver = new DriverEntity("driverTest", LocalDate.of(1995, 5, 5));
        CarEntity car = new CarEntity(regNumber, "opel", "white", true);
        driver.addNewCar(car);
        driverRepositoryTest.save(driver);

        DriverEntity requestedDriver = driverServiceTest.getDriverByCarReistrationNumber(regNumber);

        assertEquals(driver, requestedDriver);
    }

    @Test
    void shouldAddCarToDriverByDriverId() throws ObjectNotFoundException, InsuranceException {
        DriverEntity driver = new DriverEntity("driverTest", LocalDate.of(1995, 5, 5));
        CarEntity car = new CarEntity("zxc", "bmw", "blue", true);
        driverRepositoryTest.save(driver);
        int driverId = driver.getDriverId();


        driverServiceTest.addCarToDriverByDriverId(car, driverId);

        DriverEntity driverFromDatabase = driverRepositoryTest.findDriverWithCarsById(driver.getDriverId());
        assertEquals(driver, driverFromDatabase);
        assertEquals(car, driverFromDatabase.getCars().get(0));
    }
}