package com.example.Volkov.dao;

import com.example.Volkov.dto.Car;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CarsRepository {
    @Getter
    private List<Car> allCars = new ArrayList<>();

    public Car getCarById(String carId) throws ObjectNotFoundException {
        for (Car car : allCars) {
            if (car.getCarId().equalsIgnoreCase(carId)) {
                return car;
            }
        }
        throw new ObjectNotFoundException("Car not found");
    }

    public void addCar(Car car) {
        if (!allCars.contains(car)) {
            allCars.add(car);
        }
    }

    public void deleteCarById(String carId) throws ObjectNotFoundException {
        allCars.remove(getCarById(carId));
    }

    public void updateCarById(String carId, String newModel, String newColor)
            throws ObjectNotFoundException {
        Car car = getCarById(carId);
        if (newModel != null) {
            car.setModel(newModel);
        }
        if (newColor != null) {
            car.setColor(newColor);
        }
    }
}
