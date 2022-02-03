package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.WrongAgeException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ValidationService {

    public static boolean insuranceCheck(CarEntity car) throws InsuranceException {
        if (car.isInsurance()) {
            return true;
        } else {
            throw new InsuranceException("Can not add car without insurance");
        }
    }

    public static boolean driverAgeCheck(LocalDate birthDate) throws WrongAgeException {
        LocalDate today = LocalDate.now();
        if (birthDate.isAfter(today)) {
            throw new WrongAgeException("This age is not allowed");
        } else {
            int age = (int) ChronoUnit.YEARS.between(birthDate, today);
            if (age <= 18 || age >= 65) {
                throw new WrongAgeException("This age is not allowed");
            }
        }
        return true;
    }
}
