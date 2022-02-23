package com.volkov.service;

import com.volkov.db.entity.CarEntity;
import com.volkov.exceptions.ErrorType;
import com.volkov.exceptions.InsuranceException;
import com.volkov.exceptions.WrongAgeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
public class ValidationService {

    @Value("${validation.minAge: 18}")
    int minAge;

    @Value("${validation.maxAge: 65}")
    int maxAge;

    public static boolean insuranceCheck(CarEntity car) throws InsuranceException {
        if (car.isInsurance()) {
            return true;
        } else {
            throw new InsuranceException("Can not add car without insurance", ErrorType.NO_INSURANCE);
        }
    }

    public boolean driverAgeCheck(@NonNull LocalDate birthDate) throws WrongAgeException {
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
