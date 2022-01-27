package com.example.Volkov.rest;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.WrongAgeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DriverCarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DriverCarController driverCarController;

    @BeforeEach
    public void setUp() {
        Driver testDriver1 = new Driver(1,"First Driver", LocalDate.of(2000, 1, 2));
        Driver testDriver2 = new Driver(
                2,"Second Driver", LocalDate.of(2000, 12, 31));

        Car testCar1 = new Car("a1", "opel", "green", true);
        Car testCar2 = new Car("b2", "lada", "black", true);
        Car testCar3 = new Car("c3", "bmw", "white", true);
        Car testCar4 = new Car("d4", "jeep", "yellow", true);

        driverCarController.getCarService().addCar(testCar1);
        driverCarController.getCarService().addCar(testCar2);
        driverCarController.getCarService().addCar(testCar3);
        driverCarController.getCarService().addCar(testCar4);

        try {
            driverCarController.getDriverService().addDriver(testDriver1);
            driverCarController.getDriverService().addDriver(testDriver2);
        } catch (WrongAgeException e) {
            e.printStackTrace();
        }

        testDriver1.addNewCar(testCar1);
        testDriver1.addNewCar(testCar2);
        testDriver2.addNewCar(testCar3);
        testDriver2.addNewCar(testCar4);
    }


    @Test
    void shouldAddDriverToDatabase() throws Exception {
        Driver driver =
                new Driver(101, "Peter", LocalDate.of(1995, 1, 5));

        int status = mockMvc.perform((post("/controller/add_driver"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driver)))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);

    }

    @Test
    void shouldCreateCarWithRequestedParams() throws Exception {
        int status = mockMvc.perform((post("/controller/create_car?carId=xxx&model=suzuki&color=blue&insurance=true")))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);
    }

    @Test
    void shouldCreateDriverWithRequestedParams() throws Exception {
        int status =
                mockMvc.perform((post("/controller/create_driver?driverId=1000&name=Lee&birthDate=1999-02-02")))
                        .andReturn()
                        .getResponse()
                        .getStatus();

        assertEquals(201, status);
    }

    @Test
    void shouldAddNewCarToDatabase() throws Exception {
        Car car =
                new Car("x9", "niva", "blue", true);

        int status = mockMvc.perform((post("/controller/add_car?ownerId=1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);
    }

    @Test
    void shouldGetDriverCarsByDriverId() throws Exception {
        String contentAsString = mockMvc.perform(get("/controller/get_driver_cars/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Car> carList = Arrays.asList(objectMapper.readValue(contentAsString, Car[].class));

        assertEquals(1, carList.stream()
                .findAny()
                .get().getOwnerId());


    }

    @Test
    void shouldGetDriverByCarId() throws Exception {
        String contentAsString = mockMvc.perform(get("/controller/driver_by_carId?carId=B2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Driver driver = objectMapper.readValue(contentAsString, Driver.class);

        Car driversCar = driver.getCarList()
                .stream()
                .findAny().get();
        assertEquals(driversCar.getOwnerId(), driver.getDriverId());
    }

    @Test
    void shouldGetCarByCarId() throws Exception {
        String result = this.mockMvc.perform(get("/controller/car_by_Id?carId=a1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Car car = objectMapper.readValue(result, Car.class);

        assertEquals("a1", car.getCarId());

    }

    @Test
    void shouldDeleteDriverByDriverId() throws Exception {
        this.mockMvc.perform(delete("/controller/delete_driver/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    void shouldDeleteCarByCarId() throws Exception {
        this.mockMvc.perform(delete("/controller/delete_car/c3"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateDriverByDriverId() throws Exception {
        mockMvc.perform(patch("/controller/update_driver?driverId=1&name=Andrey&localDate=1989-09-19"))
                .andExpect(status().isOk());

    }

    @Test
    void shouldUpdateCarByCarId() throws Exception {
        mockMvc.perform(patch("/controller/update_car?carId=A1&model=Andrey&color=Yellow&ownerId=22"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddCarToAllCars() throws Exception {
        Car car = new Car("zzz214", "kia", "brown", true);

        int status = this.mockMvc.perform(post("/controller/add_car?ownerId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);

    }

    @Test
    void shouldGetAllDriversList() throws Exception {
        String contentAsString = this.mockMvc.perform(get("/controller/get_all_drivers/"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Driver> carList = Arrays.asList(objectMapper.readValue(contentAsString, Driver[].class));
        assertTrue(carList.contains(new Driver(
                2, "Second Driver", LocalDate.of(2000, 12, 31))));
    }

    @Test
    void shouldGetAllCarsList() throws Exception {
        String contentAsString = this.mockMvc.perform(get("/controller/get_all_cars"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Car> carList = Arrays.asList(objectMapper.readValue(contentAsString, Car[].class));
        assertTrue(carList.contains(new Car("d4", "jeep", "yellow", true)));
    }
}