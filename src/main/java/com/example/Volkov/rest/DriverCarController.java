package com.example.Volkov.rest;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import com.example.Volkov.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("controller")
public class DriverCarController {

    @Autowired
    MainService mainService;


    @PostMapping("/add_driver")
    public ResponseEntity<Driver> addDriverToDatabase(@RequestBody Driver driver) {
        HttpHeaders headers = new HttpHeaders();
        if (driver == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        mainService.driverService.addDriver(driver);
        return new ResponseEntity<>(driver, headers, HttpStatus.CREATED);
    }

    @PostMapping("/add_car")
    public ResponseEntity<Car> addCarToDatabase(@RequestBody Car car) {
        HttpHeaders headers = new HttpHeaders();
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        mainService.carService.addCar(car);
        return new ResponseEntity<>(car, headers, HttpStatus.CREATED);
    }

    @GetMapping("/get_driver_cars/{driverId}")
    public ResponseEntity<List<Car>> getDriverCars(@PathVariable int driverId) {
        try {
            return new ResponseEntity<>(mainService.driverService.getDriverById(driverId).getCarList(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/driver_by_carId")
    public ResponseEntity<Driver> getDriver(@RequestParam String carId) {
        try {
            return new ResponseEntity<>(mainService.driverService.getDriverByCarId(carId), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/car_by_Id")
    public ResponseEntity<Car> getCar(@RequestParam String carId) {
        try {
            return new ResponseEntity<>(mainService.carService.getCarById(carId), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete_driver/{driverId}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable int driverId) {
        try {
            mainService.driverService.deleteDriverById(driverId);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete_car/{carId}")
    public ResponseEntity<Car> deleteCar(@PathVariable String carId) {
        try {
            Car car = mainService.carService.getCarById(carId);
            mainService.driverService.getDriverByCarId(carId).getCarList().remove(car);
            mainService.carService.deleteCarById(carId);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update_driver")
    public ResponseEntity<Driver> updateDriver(
            @RequestParam int driverId,
            @RequestParam(value = "name", required = false) String newName,
            @RequestParam(value = "localDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newBirthDate) {

        try {
            mainService.driverService.updateDriverById(driverId, newName, newBirthDate);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update_car")
    public ResponseEntity<Car> updateCar(@RequestParam String carId,
                                         @RequestParam(required = false) String model,
                                         @RequestParam(required = false) String color) {
        try {
            mainService.carService.updateCarById(carId, model, color);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/add_car_to_driver")
    public ResponseEntity<Car> addNewCarToDriver(@RequestParam int driverId, @RequestParam Car car) {
        try {
            mainService.driverService.addCarToDriverByDriverId(car, driverId);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get_all_drivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return new ResponseEntity<>(mainService.driverService.getAllDrivers(), HttpStatus.FOUND);
    }

    @GetMapping("/get_all_cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(mainService.carService.getAllCars(), HttpStatus.FOUND);
    }

}
