package com.Volkov.rest;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.db.repo.CarRepository;
import com.Volkov.db.repo.DriverRepository;
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
    @Autowired
    DriverRepository driverRepositoryTest;
    @Autowired
    CarRepository carRepositoryTest;
//
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

        int status = mockMvc.perform((post("/controller/add_driver"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverEntity)))
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
        CarEntity carEntity =
                new CarEntity("x9", "niva", "blue", true);

        int status = mockMvc.perform((post("/controller/add_car"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carEntity)))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(201, status);
    }

    @Test
    void shouldGetDriverCarsByDriverId() throws Exception {
//        DriverEntity driver = new DriverEntity("TestDriver", LocalDate.of(2000, 1, 1));
//        CarEntity car1 = new CarEntity("a1", "lada", "white", true);
//        CarEntity car2 = new CarEntity("b2", "opel", "black", true);
//        driver.addNewCar(car1);
//        driver.addNewCar(car2);
//        driverRepositoryTest.save(driver);
//        System.out.println(driverRepositoryTest.findDriverWithCarsById(1));
//
//        String contentAsString = mockMvc.perform(get("/controller/get_driver_cars/1"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<CarEntity> carEntityList = Arrays.asList(objectMapper.readValue(contentAsString, CarEntity[].class));
//
//        assertEquals(1, carEntityList.stream()
//                .findAny()
//                .get());
    }

////    @Test
////    void shouldGetDriverByCarId() throws Exception {
////        String contentAsString = mockMvc.perform(get("/controller/driver_by_carId?carId=B2"))
////                .andExpect(status().isOk())
////                .andReturn()
////                .getResponse()
////                .getContentAsString();
////
////        DriverEntity driverEntity = objectMapper.readValue(contentAsString, DriverEntity.class);
////
////        CarEntity driversCarEntity = driverEntity.getCarEntityList()
////                .stream()
////                .findAny().get();
////        assertEquals(driversCarEntity.getOwnerId(), driverEntity.getDriverId());
////    }
//
//    @Test
//    void shouldGetCarByCarId() throws Exception {
//        String result = this.mockMvc.perform(get("/controller/car_by_Id?carId=a1"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        CarEntity carEntity = objectMapper.readValue(result, CarEntity.class);
//
//        assertEquals("a1", carEntity.getCarId());
//
//    }
//
//    @Test
//    void shouldDeleteDriverByDriverId() throws Exception {
//        this.mockMvc.perform(delete("/controller/delete_driver/1"))
//                .andExpect(status().isNoContent());
//
//    }
//
//    @Test
//    void shouldDeleteCarByCarId() throws Exception {
//        this.mockMvc.perform(delete("/controller/delete_car/c3"))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void shouldUpdateDriverByDriverId() throws Exception {
//        mockMvc.perform(patch("/controller/update_driver?driverId=1&name=Andrey&localDate=1989-09-19"))
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    void shouldUpdateCarByCarId() throws Exception {
//        mockMvc.perform(patch("/controller/update_car?carId=A1&model=Andrey&color=Yellow&ownerId=22"))
//                .andExpect(status().isOk());
//    }
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
////    @Test
////    void shouldGetAllDriversList() throws Exception {
////        String contentAsString = this.mockMvc.perform(get("/controller/get_all_drivers/"))
////                .andExpect(status().isFound())
////                .andReturn()
////                .getResponse()
////                .getContentAsString();
////
////        List<DriverEntity> carList = Arrays.asList(objectMapper.readValue(contentAsString, DriverEntity[].class));
////        assertTrue(carList.contains(new DriverEntity(
////                2, "Second Driver", LocalDate.of(2000, 12, 31))));
////    }
//
////    @Test
////    void shouldGetAllCarsList() throws Exception {
////        String contentAsString = this.mockMvc.perform(get("/controller/get_all_cars"))
////                .andExpect(status().isFound())
////                .andReturn()
////                .getResponse()
////                .getContentAsString();
////
////        List<CarEntity> carEntityList = Arrays.asList(objectMapper.readValue(contentAsString, CarEntity[].class));
////        assertTrue(carEntityList.contains(new CarEntity("d4", "jeep", "yellow", true)));
////    }
}