package com.Volkov.rest;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.dto.CarDto;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateCarWithRequestedParams() throws Exception {
        int status = mockMvc.perform(
                        (post("/cars/create?regNumber=xxx&model=suzuki&color=blue&insurance=true")))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);
    }

    @Test
    void shouldAddNewCarToDatabase() throws Exception {
        CarEntity carEntity =
                new CarEntity("x9", "niva", "blue", true);

        int status = mockMvc.perform((post("/cars/add"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carEntity)))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);
    }

    @Test
    void shouldGetCarByCarId() throws Exception {
        CarDto carDto = new CarDto("asd", "volga", "black", true, null);
        carService.addCar(carDto);
        String result = this.mockMvc.perform(get("/cars/find_by_id?carId=1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CarEntity carEntity = objectMapper.readValue(result, CarEntity.class);

        assertEquals(carDto.getRegistrationNumber(), carEntity.getRegistrationNumber());
        assertEquals(carDto.getColor(), carEntity.getColor());
    }

    @Test
    void shouldGetCarByRegNumber() throws Exception {
        CarDto carDto = new CarDto("asd", "volga", "black", true, null);
        carService.addCar(carDto);
        String result = this.mockMvc.perform(get("/cars/find_by_regNumber?regNumber=asd"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CarEntity carEntity = objectMapper.readValue(result, CarEntity.class);

        assertEquals(carDto.getRegistrationNumber(), carEntity.getRegistrationNumber());
        assertEquals(carDto.getColor(), carEntity.getColor());
    }

    @Test
    void shouldGetAllCars() throws Exception {
        carService.addCar(new CarDto());
        carService.addCar(new CarDto("test", null, null, true, null));
        carService.addCar(new CarDto("test2", null, null, false, null));
        String result = this.mockMvc.perform(get("/cars/get_all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CarDto> allCars = Arrays.asList(objectMapper.readValue(result, CarDto[].class));
        assertFalse(allCars.isEmpty());
        assertEquals(3, allCars.size());
    }

    @Test
    void shouldUpdateCarByRegNumber() throws Exception {
        carService.addCar(new CarDto("a1", null, null, true, null));

        String content = mockMvc.perform(patch("/cars/update?regNumber=a1&model=bmw&color=yellow"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CarDto carDto = objectMapper.readValue(content, CarDto.class);

        assertEquals("yellow", carDto.getColor());
        assertEquals("bmw", carDto.getModel());
        assertEquals("a1", carDto.getRegistrationNumber());
    }

    @Test
    void shouldDeleteCarByCarId() throws Exception {
        CarDto carDto = new CarDto("a1", null, null, true, null);
        carService.addCar(carDto);

        assertTrue(carService.getAllCars().contains(carDto));
        this.mockMvc.perform(delete("/cars/delete_id/1"))
                .andExpect(status().isNoContent());
        assertFalse(carService.getAllCars().contains(carDto));
    }

    @Test
    void shouldDeleteCarByRegNumber() throws Exception {
        String regNumber = "a1";
        CarDto carDto = new CarDto(regNumber, null, null, true, null);
        carService.addCar(carDto);

        assertTrue(carService.getAllCars().contains(carDto));
        this.mockMvc.perform(delete("/cars/delete_regNumber/a1"))
                .andExpect(status().isNoContent());
        assertFalse(carService.getAllCars().contains(carDto));
    }
}