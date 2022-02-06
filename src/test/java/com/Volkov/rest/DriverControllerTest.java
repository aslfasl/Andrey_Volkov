package com.Volkov.rest;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.db.repo.CarRepository;
import com.Volkov.db.repo.DriverRepository;
import com.Volkov.dto.CarDto;
import com.Volkov.dto.DriverDto;
import com.Volkov.service.DriverService;
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
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    DriverService driverService;

////    @BeforeEach
////    public void setUp() {
////        DriverEntity testDriver1Entity = new DriverEntity(1,"First Driver", LocalDate.of(2000, 1, 2));
////        DriverEntity testDriver2Entity = new DriverEntity(
////                2,"Second Driver", LocalDate.of(2000, 12, 31));
////
////        CarEntity testCar1Entity = new CarEntity("a1", "opel", "green", true);
////        CarEntity testCar2Entity = new CarEntity("b2", "lada", "black", true);
////        CarEntity testCar3Entity = new CarEntity("c3", "bmw", "white", true);
////        CarEntity testCar4Entity = new CarEntity("d4", "jeep", "yellow", true);
////
////        driverCarController.getCarService().addCar(testCar1Entity);
////        driverCarController.getCarService().addCar(testCar2Entity);
////        driverCarController.getCarService().addCar(testCar3Entity);
////        driverCarController.getCarService().addCar(testCar4Entity);
////
////        try {
////            driverCarController.getDriverService().addDriver(testDriver1Entity);
////            driverCarController.getDriverService().addDriver(testDriver2Entity);
////        } catch (WrongAgeException e) {
////            e.printStackTrace();
////        }
////
////        testDriver1Entity.addNewCar(testCar1Entity);
////        testDriver1Entity.addNewCar(testCar2Entity);
////        testDriver2Entity.addNewCar(testCar3Entity);
////        testDriver2Entity.addNewCar(testCar4Entity);
////    }


    @Test
    void shouldAddDriverToDatabase() throws Exception {
        DriverEntity driverEntity =
                new DriverEntity("Peter", LocalDate.of(1995, 1, 5));

        int status = mockMvc.perform((post("/drivers/add"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverEntity)))
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
        DriverDto driverDto = new DriverDto("Bob", LocalDate.of(2000,1,1));
        CarDto car1 = new CarDto("zaz", "opel", "green", true, null);
        CarDto car2 = new CarDto("cca", "renault", "brown", true, null);
        driverService.addDriver(driverDto);
        driverService.addCarToDriverByDriverId(car1, 1);
        driverService.addCarToDriverByDriverId(car2, 1);


        String content = mockMvc.perform(get("/drivers/get_cars/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CarDto> cars = Arrays.asList(objectMapper.readValue(content, CarDto[].class));
        assertEquals(car1, cars.get(0));
        assertEquals(car2.getRegistrationNumber(), cars.get(1).getRegistrationNumber());
    }

    @Test
    void shouldGetDriverByCarRegNumber() throws Exception {
        DriverDto driver = new DriverDto("Bill", LocalDate.of(2000, 1, 1));
        driverService.addDriver(driver);
        CarDto carDto = new CarDto("qqq", "test", "test", true, null);
        driverService.addCarToDriverByDriverId(carDto,1);

        String contentAsString = mockMvc.perform(get("/drivers/get_by_regNumber?regNumber=qqq"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        DriverDto driverDto = objectMapper.readValue(contentAsString, DriverDto.class);
        assertEquals("qqq", driverService.getDriverCarsByDriverId(1).get(0).getRegistrationNumber());
    }

//
//    @Test
//    void shouldDeleteDriverByDriverId() throws Exception {
//        this.mockMvc.perform(delete("/controller/delete_driver/1"))
//                .andExpect(status().isNoContent());
//
//    }
//

//
//    @Test
//    void shouldUpdateDriverByDriverId() throws Exception {
//        mockMvc.perform(patch("/controller/update_driver?driverId=1&name=Andrey&localDate=1989-09-19"))
//                .andExpect(status().isOk());
//
//    }
//

    //
////    @Test
////    void shouldAddCarToAllCars() throws Exception {
////        CarEntity carEntity = new CarEntity("zzz214", "kia", "brown", true);
////
////        int status = this.mockMvc.perform(post("/controller/add_car?ownerId=1")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(carEntity)))
////                .andReturn()
////                .getResponse()
////                .getStatus();
////
////        assertEquals(201, status);
////
////    }
//
    @Test
    void shouldGetAllDriversList() throws Exception {
        String contentAsString = this.mockMvc.perform(get("/controller/get_all_drivers/"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<DriverEntity> carList = Arrays.asList(objectMapper.readValue(contentAsString, DriverEntity[].class));

        System.out.println(carList);
        //        assertTrue(carList.contains(new DriverEntity(
//                2, "Second Driver", LocalDate.of(2000, 12, 31))));
    }

    @Test
    void shouldGetAllCarsList() throws Exception {
        String contentAsString = this.mockMvc.perform(get("/controller/get_all_cars"))
                .andExpect(status().isFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CarEntity> carEntityList = Arrays.asList(objectMapper.readValue(contentAsString, CarEntity[].class));
        System.out.println(carEntityList);
    }
}