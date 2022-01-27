package com.example.Volkov.rest;

import com.example.Volkov.dto.Car;
import com.example.Volkov.dto.Driver;
import com.example.Volkov.exceptions.WrongAgeException;
import com.example.Volkov.exceptions.InsuranceException;
import com.example.Volkov.exceptions.ObjectNotFoundException;
import com.example.Volkov.service.CarService;
import com.example.Volkov.service.DriverService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("controller")
@AllArgsConstructor
public class DriverCarController {

    @Getter
    private CarService carService;
    @Getter
    private DriverService driverService;

    @PostMapping("/add_driver")
    public ResponseEntity<Driver> addDriverToDatabase(@RequestBody Driver driver) {
        HttpHeaders headers = new HttpHeaders();
        if (driver == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            driverService.addDriver(driver);
        } catch (WrongAgeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(driver, headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(driver, headers, HttpStatus.CREATED);
    }

    @PostMapping("/create_car")
    public ResponseEntity<Car> createCar(
            @RequestParam String carId,
            @RequestParam String model,
            @RequestParam String color,
            @RequestParam(required = false, defaultValue = "false") boolean insurance) {
        HttpHeaders headers = new HttpHeaders();
        try {
            carService.addNewCar(carId, model, color, insurance);
        } catch (InsuranceException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/create_driver")
    public ResponseEntity<Driver> createDriver(
            @RequestParam int driverId,
            @RequestParam String name,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        HttpHeaders headers = new HttpHeaders();
        try {
            driverService.addNewDriver(driverId, name, birthDate);
        } catch (WrongAgeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/add_car")
    public ResponseEntity<Car> addCarToDatabase(@RequestBody Car car) {
        HttpHeaders headers = new HttpHeaders();
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        carService.addCar(car);
        return new ResponseEntity<>(car, headers, HttpStatus.CREATED);
    }

    @GetMapping("/get_driver_cars/{driverId}")
    public ResponseEntity<List<Car>> getDriverCars(@PathVariable int driverId) {
        try {
            return new ResponseEntity<>(driverService.getDriverById(driverId).getCarList(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/driver_by_carId")
    public ResponseEntity<Driver> getDriver(@RequestParam String carId) {
        try {
            return new ResponseEntity<>(driverService.getDriverByCarId(carId), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/car_by_Id")
    public ResponseEntity<Car> getCar(@RequestParam String carId) {
        try {
            return new ResponseEntity<>(carService.getCarById(carId), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete_driver/{driverId}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable int driverId) {
        try {
            driverService.deleteDriverById(driverId);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete_car/{carId}")
    public ResponseEntity<Car> deleteCarById(@PathVariable String carId) {
        try {
            Car car = carService.getCarById(carId);
            driverService.getDriverByCarId(carId).getCarList().remove(car);
            carService.deleteCarById(carId);
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
            driverService.updateDriverById(driverId, newName, newBirthDate);
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
            carService.updateCarById(carId, model, color);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/add_car_to_driver")
    public ResponseEntity<Car> addNewCarToDriver(@RequestParam int driverId, @RequestParam Car car) {
        try {
            driverService.addCarToDriverByDriverId(car, driverId);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get_all_drivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.FOUND);
    }

    @GetMapping("/get_all_cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.FOUND);
    }

}
