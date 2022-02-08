package com.Volkov.rest;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.repo.CarRepository;
import com.Volkov.dto.CarDto;
import com.Volkov.dto.Converter;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
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
    @Autowired
    private CarRepository carRepositoryTest;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        carRepositoryTest.deleteAll();
    }

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
        CarEntity carEntity = new CarEntity("asd", "volga", "black", true);
        carRepositoryTest.save(carEntity);
        int carId = carEntity.getCarId();

        String result = this.mockMvc.perform(get("/cars/find_by_id?carId={carId}", carId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CarEntity requestedCar = objectMapper.readValue(result, CarEntity.class);

        assertEquals(carEntity.getRegistrationNumber(), requestedCar.getRegistrationNumber());
        assertEquals(carEntity.getColor(), requestedCar.getColor());
    }

    @Test
    void shouldGetCarByRegNumber() throws Exception {
        CarEntity carEntity = new CarEntity("asd", "volga", "black", true);
        carRepositoryTest.save(carEntity);
        String regNumber = carEntity.getRegistrationNumber();

        String result = this.mockMvc.perform(get("/cars/find_by_regNumber?regNumber={regNumber}", regNumber))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CarEntity requestedCar = objectMapper.readValue(result, CarEntity.class);

        assertEquals(carEntity.getRegistrationNumber(), requestedCar.getRegistrationNumber());
        assertEquals(carEntity.getColor(), requestedCar.getColor());
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
        String regNumber = "a";
        CarEntity carEntity = new CarEntity(regNumber, "test", "test", true);
        carRepositoryTest.save(carEntity);

        String content = mockMvc.perform(patch(
                "/cars/update?regNumber={regNumber}&model=bmw&color=yellow", regNumber))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CarDto carDto = objectMapper.readValue(content, CarDto.class);

        assertEquals("yellow", carDto.getColor());
        assertEquals("bmw", carDto.getModel());
        assertEquals(regNumber, carDto.getRegistrationNumber());
    }

    @Test
    void shouldDeleteCarByCarId() throws Exception {
        CarEntity carEntity = new CarEntity("a1", null, null, true);
        carRepositoryTest.save(carEntity);
        int carId = carEntity.getCarId();
        CarDto carDto = objectMapper.convertValue(carEntity, CarDto.class);

        assertTrue(carService.getAllCars().contains(carDto));
        this.mockMvc.perform(delete("/cars/delete_id/{carId}", carId))
                .andExpect(status().isNoContent());
        assertFalse(carService.getAllCars().contains(carDto));
    }

    @Test
    void shouldDeleteCarByRegNumber() throws Exception {
        String regNumber = "a1";
        CarDto carDto = new CarDto(regNumber, null, null, true, null);
        carService.addCar(carDto);

        assertTrue(carService.getAllCars().contains(carDto));
        this.mockMvc.perform(delete("/cars/delete_regNumber/{regNumber}", regNumber))
                .andExpect(status().isNoContent());
        assertFalse(carService.getAllCars().contains(carDto));
    }
}