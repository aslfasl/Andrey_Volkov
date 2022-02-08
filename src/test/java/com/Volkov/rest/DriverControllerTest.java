package com.Volkov.rest;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.db.repo.CarRepository;
import com.Volkov.db.repo.DriverRepository;
import com.Volkov.dto.CarDto;
import com.Volkov.dto.DriverDto;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.service.DriverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DriverService driverService;
    @Autowired
    DriverRepository driverRepositoryTest;
    @Autowired
    CarRepository carRepositoryTest;


    @AfterEach
    void tearDown() {
        driverRepositoryTest.deleteAll();
        carRepositoryTest.deleteAll();
    }

    @Test
    void shouldAddDriverToDatabase() throws Exception {
        DriverDto driverDto =
                new DriverDto("Peter", LocalDate.of(1995, 1, 5));

        int status = mockMvc.perform((post("/drivers/add"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverDto)))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);
    }


    @Test
    void shouldCreateDriverWithRequestedParams() throws Exception {
        String content =
                mockMvc.perform((post("/drivers/create?name=Lee&birthDate=1999-02-02")))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        DriverDto driverDto = objectMapper.readValue(content, DriverDto.class);

        assertEquals("Lee", driverDto.getName());
    }


    @Test
    void shouldGetDriverCarsByDriverId() throws Exception {
        DriverEntity driverEntity = new DriverEntity("Bob", LocalDate.of(2000, 1, 1));
        CarEntity car1 = new CarEntity("zaz", "opel", "green", true);
        CarEntity car2 = new CarEntity("cca", "renault", "brown", true);
        driverEntity.addNewCar(car1);
        driverEntity.addNewCar(car2);
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();


        String content = mockMvc.perform(get("/drivers/get_cars/{driverId}", driverId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CarDto> cars = Arrays.asList(objectMapper.readValue(content, CarDto[].class));
        assertEquals(car1.getRegistrationNumber(), cars.get(0).getRegistrationNumber());
        assertEquals(car2.getRegistrationNumber(), cars.get(1).getRegistrationNumber());
    }

    @Test
    void shouldGetDriverByCarRegNumber() throws Exception {
        DriverEntity driverEntity = new DriverEntity("Bill", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();
        CarDto carDto = new CarDto("qqq", "test", "test", true, null);
        driverService.addCarToDriverByDriverId(carDto, driverId);

        String contentAsString = mockMvc.perform(get("/drivers/get_by_regNumber?regNumber=qqq"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        DriverDto driverDto = objectMapper.readValue(contentAsString, DriverDto.class);

        assertEquals("qqq", driverService.getDriverCarsByDriverId(driverId).get(0).getRegistrationNumber());
        assertEquals(driverEntity.getName(), driverDto.getName());
        assertEquals(driverEntity.getBirthDate(), driverDto.getBirthDate());
    }

    @Test
    void shouldGetDriverById() throws Exception {
        DriverEntity driverEntity = new DriverEntity("Bill", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();

        String contentAsString = mockMvc.perform(get("/drivers/get_by_id/{driverId}", driverId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        DriverDto driverDto = objectMapper.readValue(contentAsString, DriverDto.class);
        assertEquals(driverEntity.getName(), driverDto.getName());
        assertEquals(driverEntity.getBirthDate(), driverDto.getBirthDate());
    }

    @Test
    void shouldGetAllDrivers() throws Exception {
        DriverDto driverDto1 = new DriverDto("Bill", LocalDate.of(2000, 1, 1));
        DriverDto driverDto2 = new DriverDto("Bob", LocalDate.of(2002, 2, 2));
        driverService.addDriver(driverDto1);
        driverService.addDriver(driverDto2);

        String content = mockMvc.perform(get("/drivers/get_all"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<DriverDto> drivers = Arrays.asList(objectMapper.readValue(content, DriverDto[].class));

        assertEquals(2, drivers.size());
        assertEquals(driverDto1.getName(), drivers.get(0).getName());
        assertEquals(driverDto2.getBirthDate(), drivers.get(1).getBirthDate());
    }


    @Test
    void shouldDeleteDriverByDriverId() throws Exception {
        DriverEntity driverEntity = new DriverEntity("Bill", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();

        assertTrue(driverService.getDriverById(driverId).getName().equalsIgnoreCase(driverEntity.getName()));
        this.mockMvc.perform(delete("/drivers/delete/{driverId}", driverId))
                .andExpect(status().isNoContent());

        ObjectNotFoundException exception =
                assertThrows(ObjectNotFoundException.class, () -> driverService.getDriverById(driverId));
        assertEquals("Driver not found", exception.getMessage());
    }

    @Test
    void shouldUpdateDriverByDriverId() throws Exception {
        DriverEntity driverEntity = new DriverEntity("Bill", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();

        String content = mockMvc.perform(
                patch("/drivers/update?driverId={driverId}&name=Andrey&localDate=1989-09-19", driverId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        DriverDto driverPatched = objectMapper.readValue(content, DriverDto.class);

        assertEquals("Andrey", driverPatched.getName());
        assertEquals(LocalDate.of(1989, 9, 19), driverPatched.getBirthDate());
    }

    @Test
    void shouldAddCarToDriverByDriverId() throws Exception {
        DriverEntity driverEntity = new DriverEntity("Bill", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();
        CarDto carDto = new CarDto("test", "test", "test", true, null);

        mockMvc.perform((post("/drivers/add_car?driverId={driverId}", driverId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDto)))
                .andExpect(status().isOk());
    }
}