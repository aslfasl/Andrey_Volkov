package com.example.Volkov.rest;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    void shoudCreateCarWithRequestedParams() throws Exception {
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
        assertTrue(carList.contains(new Car("b2", "lada", "black", true)));


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
                2,"Second Driver", LocalDate.of(2000, 12, 31))));
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