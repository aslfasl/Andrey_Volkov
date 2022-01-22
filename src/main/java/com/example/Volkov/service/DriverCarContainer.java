package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;

import java.util.*;

public class DriverCarContainer {
//    Map<Integer, List<Car>> driverCarMap = new HashMap<>();
//    List<Car> allCars = new ArrayList<>();
//    List<Driver> allDrivers = new ArrayList<>();
//
//    public void addDriver(Driver driver) {
//        if (!allDrivers.contains(driver)) {
//            driverCarMap.put(driver.getDriverId(), new ArrayList<>());
//            allDrivers.add(driver);
//        }
//    }
//
//    public void addCar(Car car, int ownerId) {
//        if (!allCars.contains(car) && driverCarMap.containsKey(ownerId)) {
//            allCars.add(car);
//            driverCarMap.get(ownerId).add(car);
//        }
//    }
//
//    public List<Car> getCarsByDriverId(int driverId) {
//        List<Car> requestList = new ArrayList<>();
//        if (driverCarMap.containsKey(driverId)) {
//            requestList = driverCarMap.get(driverId);
//        }
//        return requestList;
//    }
//
//    public Driver getDriverById(int driverId) {
//        for (Driver driver : allDrivers) {
//            if (driver.getDriverId() == driverId) {
//                return driver;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
//
//    public Car getCarById(String carId) {
//        for (Car car: allCars) {
//            if (car.getCarId().equalsIgnoreCase(carId)) {
//                return car;
//            }
//        }
//        throw new IllegalArgumentException();
//    }
//
//    public Driver getDriverByCarId(String carId) {
//        for (Map.Entry<Integer, List<Car>> entry : driverCarMap.entrySet()) {
//            for (Car car : entry.getValue()) {
//                if (car.getCarId().equalsIgnoreCase(carId)) {
//                    return getDriverById(entry.getKey());
//                }
//            }
//        }
//        throw new IllegalArgumentException();
//    }
//
//    public void deleteDriverById(int driverId) {
//        if (driverCarMap.containsKey(driverId)) {
//            allDrivers.remove(getDriverById(driverId));
//            driverCarMap.remove(driverId);
//        }
//    }
//
//    public void deleteCarById(String carId) {
//        allCars.removeIf(car -> car.getCarId().equalsIgnoreCase(carId));
//        for (Map.Entry<Integer, List<Car>> entry : driverCarMap.entrySet()) {
//            List<Car> carList = entry.getValue();
//            carList.removeIf(car -> car.getCarId().equalsIgnoreCase(carId));
//        }
//    }
//
//    public void changeDriverNameById(int driverId, String name) {
//        try {
//            Driver driver = getDriverById(driverId);
//            driver.setName(name);
//        } catch (IllegalArgumentException e) {
//            System.out.println("No driver with such ID");
//        }
//    }
//
//    public void updateCarById(String carId, String model, String color) {
//        Car car = getCarById(carId);
//        car.setModel(model);
//        car.setColor(color);
//    }
//
//
}
