package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.InsuranceException;
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
class CarServiceTest {

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService();

        Car testCar1 = new Car("a1", "opel", "green", true);
        Car testCar2 = new Car("b2", "lada", "black", true);
        Car testCar3 = new Car("c3", "bmw", "white", true);
        Car testCar4 = new Car("d4", "jeep", "yellow", true);

        carService.addCar(testCar1);
        carService.addCar(testCar2);
        carService.addCar(testCar3);
        carService.addCar(testCar4);
    }

    @Test
    void canGetCarByCarId() throws ObjectNotFoundException {
        String carId = "a1";
        Car car = carService.getCarById(carId);
        assertEquals(car.getCarId(), carId);
    }

    @Test
    void canAddNewCar() throws InsuranceException {
        String carId = "add1";
        String model = "pajero";
        String color = "blue";
        boolean insurance = true;
        Car car = new Car(carId, model, color, insurance);

        assertFalse(carService.getAllCars().contains(car));
        carService.addNewCar(carId, model, color, insurance);
        assertTrue(carService.getAllCars().contains(car));

    }

    @Test
    void canAddCar() {
        Car car = new Car("add2", "opel", "black", true);
        assertFalse(carService.getAllCars().contains(car));
        carService.addCar(car);
        assertTrue(carService.getAllCars().contains(car));
    }

    @Test
    void canDeleteCarById() throws ObjectNotFoundException {
        Car car = carService
                .getAllCars()
                .stream()
                .findAny()
                .get();

        assertTrue(carService.getAllCars().contains(car));
        carService.deleteCarById(car.getCarId());
        assertFalse(carService.getAllCars().contains(car));
    }

    @Test
    void canUpdateCarById() throws ObjectNotFoundException {
        String carId = "a1";
        Car car = carService.getCarById(carId);
        String model = "lefan";
        String color = "purple";
        carService.updateCarById(carId, model, color);
        assertEquals(carId, car.getCarId());
        assertEquals(model, car.getModel());
        assertEquals(color, car.getColor());
    }

    @Test
    void getAllCars() {
        //lombok
    }
}