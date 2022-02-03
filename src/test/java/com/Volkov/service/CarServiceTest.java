package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.repo.CarRepository;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.service.CarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private CarRepository carRepositoryTest;

    @Autowired
    private CarService carServiceTest;

    @AfterEach
    void tearDown() {
        carRepositoryTest.deleteAll();
    }

    @Test
    void shouldGetCarByCarId() throws ObjectNotFoundException {
        CarEntity car = new CarEntity();
        car.setColor("black");
        car.setModel("record");
        car.setInsurance(true);
        car.setRegistrationNumber("a100");

        carRepositoryTest.save(car);
        int carId = car.getCarId();
        CarEntity carInDatabase = carServiceTest.getCarById(carId);
        assertEquals(car, carInDatabase);
    }

    @Test
    void shouldThrowObjectNotFoundExceptionAfterGetCarById() throws ObjectNotFoundException {
        CarEntity car = new CarEntity();
        carRepositoryTest.save(car);

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class,
                () -> carServiceTest.getCarById(2));

        assertEquals("Car not found", thrown.getMessage());
    }

    @Test
    void shouldAddNewCarWithGivenParameters() throws ObjectAlreadyExistsException {
        String regNumber = "add1";
        String model = "pajero";
        String color = "blue";
        boolean insurance = true;
        CarEntity car = new CarEntity(regNumber, model, color, insurance);

        assertFalse(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
        carServiceTest.addNewCar(regNumber, model, color, insurance);
        assertTrue(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
    }

    @Test
    void shouldThrowObjectAlreadyExistsExceptionWhenAddNewCarWithGivenParameters() {
        CarEntity car = new CarEntity();
        car.setRegistrationNumber("test");
        carRepositoryTest.save(car);

        ObjectAlreadyExistsException exception = assertThrows(ObjectAlreadyExistsException.class, () ->
                carServiceTest.addNewCar("test", "test", "test", false));

        assertEquals("Car with the same registration number already exists", exception.getMessage());
    }

    @Test
    void shouldAddCarToDatabase() throws ObjectAlreadyExistsException {
        String regNumber = "add2";
        CarEntity car = new CarEntity(regNumber, "opel", "black", true);
        assertFalse(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
        carServiceTest.addCar(car);
        assertTrue(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
    }

    @Test
    void shouldThrowObjectAlreadyExistsExceptionWhenAddCar() {
        CarEntity car = new CarEntity();
        car.setRegistrationNumber("test");
        carRepositoryTest.save(car);

        ObjectAlreadyExistsException exception = assertThrows(ObjectAlreadyExistsException.class, () ->
                carServiceTest.addCar(car));

        assertEquals("Car with the same registration number already exists", exception.getMessage());
    }


    @Test
    void shouldDeleteCarById() {
        String regNumber = "z001";
        CarEntity car = new CarEntity(regNumber, "model", "color", true);
        carRepositoryTest.save(car);

        assertTrue(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
        carServiceTest.deleteCarByRegistrationNumber(regNumber);
        assertFalse(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
    }

    @Test
    void shouldUpdateCarByRegistrationNumber() {
        String regNumber = "a1";
        String model = "lefan";
        String color = "purple";
        CarEntity car = new CarEntity(regNumber, null, null, true);
        carRepositoryTest.save(car);

        carServiceTest.updateCarByRegistrationNumber(regNumber, model, color);
        car = carRepositoryTest.getCarEntityByRegistrationNumber(regNumber);

        assertEquals(regNumber, car.getRegistrationNumber());
        assertEquals(model, car.getModel());
        assertEquals(color, car.getColor());
    }

}