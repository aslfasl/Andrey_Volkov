package com.example.Volkov.web;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.dto.DriverCarContainer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("controller")
public class DriverCarController {

    DriverCarContainer driverCarContainer = new DriverCarContainer();

    @PostMapping("/add_driver")
    public String addDriverToDatabase(@RequestBody Driver driver) {
        driverCarContainer.addDriver(driver);
        return "Driver " + driver.getName() + " added";
    }

    @PostMapping("/add_car")
    public String addCarToDatabase(@RequestBody Car car, @RequestParam int ownerId) {
        driverCarContainer.addCar(car, ownerId);
        return "Car has been added";
    }

    @GetMapping("/get_driver_cars/{driverId}")
    public List<Car> getDriverCars(@PathVariable int driverId) {
        return driverCarContainer.getCarsByDriverId(driverId);
    }

    @GetMapping("/driver_by_carId")
    public Driver getDriver(@RequestParam String carId) {
        try {
            return driverCarContainer.getDriverByCarId(carId);
        } catch (IllegalArgumentException e) {
            System.out.println("ObjectNotFound");
            return new Driver(0, "");
        }
    }

    @GetMapping("/car_by_Id")
    public Car getCar(@RequestParam String carId) {
        try {
            return driverCarContainer.getCarById(carId);
        } catch (IllegalArgumentException e) {
            System.out.println("ObjectNotFound");
            return new Car("", "", "");
        }
    }

    @DeleteMapping("/delete_driver/{driverId}")
    public String deleteDriver(@PathVariable int driverId) {
        driverCarContainer.deleteDriverById(driverId);
        return "Driver deleted";
    }

    @DeleteMapping("/delete_car/{carId}")
    public String deleteCar(@PathVariable String carId) {
        driverCarContainer.deleteCarById(carId);
        return "Car deleted";
    }

    @PatchMapping("/update_driver")
    public String updateDriver(@RequestParam int driverId, @RequestParam String name) {
        driverCarContainer.changeDriverNameById(driverId, name);
        return "Driver's name updated";
    }

    @PatchMapping("/update_car")
    public String updateCar(@RequestParam String carId, @RequestParam String model, @RequestParam String color) {
        driverCarContainer.updateCarById(carId, model, color);
        return "Car updated";
    }



}
