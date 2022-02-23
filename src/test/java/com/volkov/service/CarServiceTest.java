package com.volkov.service;

import com.volkov.db.entity.CarEntity;
import com.volkov.db.repo.CarRepository;
import com.volkov.dto.CarDto;
import com.volkov.dto.Converter;
import com.volkov.exceptions.ObjectAlreadyExistsException;
import com.volkov.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarServiceTest {

    @Autowired
    private Converter converter;

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
        CarEntity car = new com.volkov.db.entity.CarEntity();
        car.setColor("black");
        car.setModel("record");
        car.setInsurance(true);
        car.setRegistrationNumber("a100");
        carRepositoryTest.save(car);
        int carId = car.getCarId();

        CarDto carDto = carServiceTest.getCarById(carId);

        assertEquals(car.getRegistrationNumber(), carDto.getRegistrationNumber());
        assertEquals(car.getColor(), carDto.getColor());
        assertEquals(car.getModel(), carDto.getModel());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionAfterGetCarById() {
        CarEntity car = new com.volkov.db.entity.CarEntity();
        carRepositoryTest.save(car);

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class,
                () -> carServiceTest.getCarById(2));

        assertEquals("Car not found", thrown.getMessage());
    }

    @Test
    void shouldGetCarByRegNumber() throws ObjectNotFoundException {
        String regNumber = "aaa";
        CarEntity carEntity = new CarEntity(regNumber, "toyota", "blue", false);
        carRepositoryTest.save(carEntity);

        CarDto carDto = carServiceTest.getCarByRegistrationNumber(regNumber);

        assertEquals(carEntity.getRegistrationNumber(), carDto.getRegistrationNumber());
        assertEquals(carEntity.getColor(), carDto.getColor());
        assertEquals(carEntity.getModel(), carDto.getModel());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenGetCarByRegNumber() {
        String regNumber = "aaa";

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class,
                () -> carServiceTest.getCarByRegistrationNumber(regNumber));

        assertEquals("Car not found", thrown.getMessage());
    }

    @Test
    void shouldAddNewCarWithGivenParameters() throws ObjectAlreadyExistsException {
        String regNumber = "add1";
        String model = "pajero";
        String color = "blue";
        boolean insurance = true;

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
        CarDto carDto = new CarDto(regNumber, "opel", "black", true, null);
        assertFalse(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
        carServiceTest.addCar(carDto);
        assertTrue(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
    }

    @Test
    void shouldThrowObjectAlreadyExistsExceptionWhenAddCar() {
        CarEntity carEntity = new CarEntity();
        carEntity.setRegistrationNumber("test");
        carRepositoryTest.save(carEntity);
        CarDto carDto = converter.convertValue(carEntity, CarDto.class);

        ObjectAlreadyExistsException exception = assertThrows(ObjectAlreadyExistsException.class, () ->
                carServiceTest.addCar(carDto));

        assertEquals("Car with the same registration number already exists", exception.getMessage());
    }


    @Test
    void shouldDeleteCarByRegNumber() {
        String regNumber = "z001";
        CarEntity car = new CarEntity(regNumber, "model", "color", true);
        carRepositoryTest.save(car);

        assertTrue(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
        carServiceTest.deleteCarByRegistrationNumber(regNumber);
        assertFalse(carRepositoryTest.existsCarByRegistrationNumber(regNumber));
    }

    @Test
    void shouldDeleteCarById() {
        CarEntity car = new CarEntity("number", "model", "color", true);
        carRepositoryTest.save(car);
        int carId = car.getCarId();

        assertTrue(carRepositoryTest.existsById(carId));
        carServiceTest.deleteCarById(carId);
        assertFalse(carRepositoryTest.existsById(carId));
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

    @Test
    void shouldGetAllCars() {
        CarEntity car1 = new CarEntity("a1", "model", "color", true);
        CarEntity car2 = new CarEntity("b2", "model", "color", true);
        CarEntity car3 = new CarEntity("c3", "model", "color", true);
        carRepositoryTest.save(car1);
        carRepositoryTest.save(car2);
        carRepositoryTest.save(car3);

        List<CarDto> allCars = carServiceTest.getAllCars();

        assertEquals(3, allCars.size());
        assertEquals("a1", allCars.get(0).getRegistrationNumber());
        assertEquals("b2", allCars.get(1).getRegistrationNumber());
        assertEquals("c3", allCars.get(2).getRegistrationNumber());
    }
}