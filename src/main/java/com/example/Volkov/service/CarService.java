package com.example.Volkov.service;

import com.example.Volkov.dto.Car;
import com.example.Volkov.exceptions.InsuranceException;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
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

    public boolean insuranceCheck(Car car) throws InsuranceException {
        if (car.isInsurance()) {
            return true;
        } else {
            throw new InsuranceException("Can not add car without insurance");
        }
    }
    public void addNewCar(String carId, String model, String color, boolean insurance, int ownerId)
            throws InsuranceException {
        Car car = new Car(carId, model, color, insurance);
        car.setOwnerId(ownerId);
        if (insuranceCheck(car)){
            allCars.add(car);
        }


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
