package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.db.repo.DriverRepository;
import com.Volkov.dto.CarDto;
import com.Volkov.dto.Converter;
import com.Volkov.dto.DriverDto;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.exceptions.WrongAgeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Driver;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceTest {

    @Autowired
    DriverService driverServiceTest;

    @Autowired
    DriverRepository driverRepositoryTest;

    @AfterEach
    void tearDown() {
        driverRepositoryTest.deleteAll();
    }

    ////    @BeforeEach
////    void setUp() {
////        DriverEntity testDriver1Entity = new DriverEntity(1, "First Driver", LocalDate.of(2000, 1, 2));
////        DriverEntity testDriver2Entity = new DriverEntity(
////                2, "Second Driver", LocalDate.of(2000, 12, 31));
////
////        CarEntity testCar1Entity = new CarEntity("a1", "opel", "green", true);
////        CarEntity testCar2Entity = new CarEntity("b2", "lada", "black", true);
////        CarEntity testCar3Entity = new CarEntity("c3", "bmw", "white", true);
////        CarEntity testCar4Entity = new CarEntity("d4", "jeep", "yellow", true);
////
////        try {
////            driverService.addDriver(testDriver1Entity);
////            driverService.addDriver(testDriver2Entity);
////        } catch (WrongAgeException e) {
////            e.printStackTrace();
////        }
////
////        testDriver1Entity.addNewCar(testCar1Entity);
////        testDriver1Entity.addNewCar(testCar2Entity);
////        testDriver2Entity.addNewCar(testCar3Entity);
////        testDriver2Entity.addNewCar(testCar4Entity);
////    }
//
    @Test
    void shouldAddNewDriverWhenOneIsGiven() throws WrongAgeException, ObjectAlreadyExistsException {
        String name = "TestDriver";
        LocalDate birthdate = LocalDate.of(2002, 2, 3);
        DriverDto driverDto = new DriverDto(name, birthdate);

        assertFalse(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
        driverServiceTest.addDriver(driverDto);
        assertTrue(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
    }

    @Test
    void shouldThrowObjectAlreadyExistsExceptionWhenAddDriver() {
        DriverEntity driverEntity = new DriverEntity("Name", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driverEntity);
        DriverDto driverDto = Converter.convertValue(driverEntity, DriverDto.class);

        ObjectAlreadyExistsException e =
                assertThrows(ObjectAlreadyExistsException.class, () -> driverServiceTest.addDriver(driverDto));

        assertEquals("Driver already exists", e.getMessage());
    }

    @Test
    void shouldThrowWrongAgeException() {
        String name = "TestDriver";
        LocalDate birthdate = LocalDate.of(1002, 2, 3);
        DriverDto driverDto = new DriverDto(name, birthdate);

        WrongAgeException exception = assertThrows(WrongAgeException.class, () ->
                driverServiceTest.addDriver(driverDto));

        assertEquals("This age is not allowed", exception.getMessage());
    }

    @Test
    void shouldTakeParametersAndAddNewDriver() throws WrongAgeException {
        String name = "add1";
        LocalDate birthdate = LocalDate.of(1969, 2, 12);

        assertFalse(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
        driverServiceTest.addNewDriver(name, birthdate);
        assertTrue(driverRepositoryTest.existsDriverByNameAndBirthDate(name, birthdate));
    }

    @Test
    void shouldGetDriverById() throws ObjectNotFoundException, InsuranceException, ObjectAlreadyExistsException {
        DriverEntity driverEntity = new DriverEntity("driverTest", LocalDate.of(1995, 5, 5));
        CarEntity car = new CarEntity("aaa", "opel", "white", true);
        driverEntity.addNewCar(car);
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();

        DriverDto driverDto = driverServiceTest.getDriverById(driverId);

        assertEquals(driverEntity.getName(), driverDto.getName());
        assertEquals(driverEntity.getBirthDate(), driverDto.getBirthDate());
    }

    @Test
    void shouldThrowObjectNotFoundExceptionOnGetDriverById() {
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
                () -> driverServiceTest.getDriverById(1));

        assertEquals("Driver not found", exception.getMessage());
    }

    @Test
    void shouldDeleteDriverById() {
        DriverEntity driver =
                new DriverEntity("testName", LocalDate.of(2000, 1, 1));
        driverRepositoryTest.save(driver);

        assertTrue(driverRepositoryTest.existsDriverByNameAndBirthDate(driver.getName(), driver.getBirthDate()));
        driverServiceTest.deleteDriverById(driver.getDriverId());
        assertFalse(driverRepositoryTest.existsDriverByNameAndBirthDate(driver.getName(), driver.getBirthDate()));
    }


    @Test
    void shouldUpdateDriverByIdWithAFollowingParameters() throws ObjectNotFoundException {
        String newName = "NewDriverName";
        LocalDate newBirthDate = LocalDate.of(1965, 3, 20);
        DriverEntity driverEntity = new DriverEntity(null, null);
        driverRepositoryTest.save(driverEntity);
        int driverId = driverEntity.getDriverId();

        driverServiceTest.updateDriverById(driverId, newName, newBirthDate);
        DriverDto driverDto = driverServiceTest.getDriverById(driverId);

        assertEquals(newName, driverDto.getName());
        assertEquals(newBirthDate, driverDto.getBirthDate());
    }

    @Test
    void shouldGetDriverByCarRegistrationNumber() throws InsuranceException, ObjectAlreadyExistsException {
        String regNumber = "d4";
        DriverEntity driver = new DriverEntity("driverTest", LocalDate.of(1995, 5, 5));
        CarEntity car = new CarEntity(regNumber, "opel", "white", true);
        driver.addNewCar(car);
        driverRepositoryTest.save(driver);

        DriverDto requestedDriver = driverServiceTest.getDriverByCarRegistrationNumber(regNumber);

        assertEquals(driver.getName(), requestedDriver.getName());
        assertEquals(driver.getBirthDate(), requestedDriver.getBirthDate());
    }

    @Test
    void shouldGetAllDrivers() {
        DriverEntity driverEntity1 = new DriverEntity();
        driverEntity1.setName("first");
        DriverEntity driverEntity2 = new DriverEntity();
        driverEntity2.setName("second");
        DriverEntity driverEntity3 = new DriverEntity();
        driverEntity3.setName("third");
        driverRepositoryTest.save(driverEntity1);
        driverRepositoryTest.save(driverEntity2);
        driverRepositoryTest.save(driverEntity3);

        List<DriverDto> driverDtoList = driverServiceTest.getAllDrivers();

        assertEquals(3, driverDtoList.size());
        assertEquals(driverEntity1.getName(), driverDtoList.get(0).getName());
    }

    @Test
    void shouldAddCarToDriverByDriverId() throws InsuranceException, ObjectAlreadyExistsException {
        DriverEntity driver = new DriverEntity("driverTest", LocalDate.of(1995, 5, 5));
        CarDto carDto = new CarDto("zxc", "bmw", "blue", true, null);
        driverRepositoryTest.save(driver);
        int driverId = driver.getDriverId();

        driverServiceTest.addCarToDriverByDriverId(carDto, driverId);

        DriverEntity driverFromDatabase = driverRepositoryTest.findDriverWithCarsById(driver.getDriverId());
        assertEquals(driver, driverFromDatabase);
        assertEquals(carDto.getRegistrationNumber(), driverFromDatabase.getCars().get(0).getRegistrationNumber());
    }

    @Test
    void shouldGetAllDriverCarsByDriverId() throws InsuranceException, ObjectAlreadyExistsException {
        DriverEntity driverEntity = new DriverEntity("TestName", LocalDate.of(2000, 1, 1));
        CarEntity carEntity = new CarEntity("oo1", "bmw", "white", true);
        driverEntity.addNewCar(carEntity);
        driverRepositoryTest.save(driverEntity);

        List<CarDto> carDtos = driverServiceTest.getDriverCarsByDriverId(driverEntity.getDriverId());

        assertEquals(driverEntity.getCars().size(), carDtos.size());
        assertEquals(carEntity.getRegistrationNumber(), carDtos.get(0).getRegistrationNumber());
    }
}