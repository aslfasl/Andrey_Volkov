package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.exceptions.ErrorType;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.WrongAgeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@RequiredArgsConstructor
public class ValidationService {

    @Value("${validation.minAge}")
    int minAge;

    @Value("${validation.maxAge}")
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
