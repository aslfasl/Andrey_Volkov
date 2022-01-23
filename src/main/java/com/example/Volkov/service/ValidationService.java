package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.exceptions.WrongAgeException;
import com.example.Volkov.exceptions.InsuranceException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ValidationService {

    public static boolean insuranceCheck(Car car) throws InsuranceException {
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
