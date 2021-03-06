package com.volkov.service;

import com.volkov.db.entity.DriverEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ValidationServiceTest {

    @Autowired
    ValidationService validationService;

//    @Test
//    void shouldCheckInsurance() throws InsuranceException {
//        CarEntity carEntity = new CarEntity("qwerty", "renault", "white", true);
//        assertTrue(ValidationService.insuranceCheck(carEntity));
//    }
//
//    @Test
//    void shouldThrowInsuranceException() {
//        CarEntity carEntity = new CarEntity("qwerty", "renault", "white", false);
//        assertThrows(InsuranceException.class, () -> {
//            ValidationService.insuranceCheck(carEntity);
//        });
//    }

    @Test
    void shouldCheckDriversAge() {
        DriverEntity driverEntity = new DriverEntity( "James", LocalDate.of(1970, 6,17));
        assertTrue(validationService.driverAgeCheck(driverEntity.getBirthDate()));
    }
//
//    @Test
//    void shouldThrowWrongAgeException() {
//        DriverEntity driverEntity = new DriverEntity(55, "James", LocalDate.of(970, 6,17));
//        assertThrows(WrongAgeException.class, () -> {
//            ValidationService.driverAgeCheck(driverEntity.getDateOfBirth());
//        });
//    }
}