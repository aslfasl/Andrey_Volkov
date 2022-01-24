package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.WrongAgeException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MainService {
    public CarService carService = new CarService();
    public DriverService driverService = new DriverService();

    {
        Driver testDriver1 = new Driver(1,"First Driver", LocalDate.of(2000, 1, 2));
        Driver testDriver2 = new Driver(
                2,"Second Driver", LocalDate.of(2000, 12, 31));

        Car testCar1 = new Car("a1", "opel", "green", true);
        Car testCar2 = new Car("b2", "lada", "black", true);
        Car testCar3 = new Car("c3", "bmw", "white", true);
        Car testCar4 = new Car("d4", "jeep", "yellow", true);

        carService.addCar(testCar1);
        carService.addCar(testCar2);
        carService.addCar(testCar3);
        carService.addCar(testCar4);

        try {
            driverService.addDriver(testDriver1);
            driverService.addDriver(testDriver2);
        } catch (WrongAgeException e) {
            e.printStackTrace();
        }


        testDriver1.addNewCar(testCar1);
        testDriver1.addNewCar(testCar2);
        testDriver2.addNewCar(testCar3);
        testDriver2.addNewCar(testCar4);
    }

    public static void main(String[] args) {
        Car testCar1 = new Car("a1", "opel", "green", true);
        System.out.println(testCar1);
        Boolean x = testCar1.toString().contains("carId=a1");
        System.out.println(x);
    }
}
