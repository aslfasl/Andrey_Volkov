package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarServiceTest {

    @Autowired
    private MainService mainService;

    @BeforeEach
    void setUp(){
        mainService = new MainService();
    }

    @Test
    void canGetCarByCarId() throws ObjectNotFoundException {
        String carId = "a1";
        Car car = mainService.carService.getCarById(carId);
        assertEquals(car.getCarId(), carId);
    }

    @Test
    void addNewCar() {

    }

    @Test
    void addCar() {
        //given

    }

    @Test
    void deleteCarById() {
    }

    @Test
    void updateCarById() {
    }

    @Test
    void getAllCars() {
    }
}