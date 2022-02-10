package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.exceptions.ErrorType;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.WrongAgeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ValidationService {

    @Value("${custom.validation.minAge}")
    static int minAge;

    @Value("${custom.validation.maxAge}")
    static int maxAge;

    public static boolean insuranceCheck(CarEntity car) throws InsuranceException {
        if (car.isInsurance()) {
            return true;
        } else {
            throw new InsuranceException("Can not add car without insurance", ErrorType.NO_INSURANCE);
        }
    }

    public static boolean driverAgeCheck(LocalDate birthDate) throws WrongAgeException {
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) {
            throw new WrongAgeException("This age is not allowed", ErrorType.WRONG_AGE);
        } else {
            int age = (int) ChronoUnit.YEARS.between(birthDate, today);
            if (age < minAge || age > maxAge) {
                throw new WrongAgeException("This age is not allowed", ErrorType.WRONG_AGE);
            }
        }
        return true;
    }
}
