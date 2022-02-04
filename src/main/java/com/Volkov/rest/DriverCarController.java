package com.Volkov.rest;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.exceptions.WrongAgeException;
import com.Volkov.service.DriverService;
import com.Volkov.service.CarService;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@AllArgsConstructor
public class DriverCarController {

    private DriverService driverService;
    private CarService carService;

    @PostMapping("/add_driver")
    public ResponseEntity<DriverEntity> addDriverToDatabase(@RequestBody DriverEntity driverEntity) {
        HttpHeaders headers = new HttpHeaders();
        if (driverEntity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            driverService.addDriver(driverEntity);
        } catch (WrongAgeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(driverEntity, headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(driverEntity, headers, HttpStatus.CREATED);
    }

    @PostMapping("/create_car")
    public ResponseEntity<CarEntity> createCar(
            @RequestParam String carId,
            @RequestParam String model,
            @RequestParam String color,
            @RequestParam(required = false, defaultValue = "false") boolean insurance) {
        HttpHeaders headers = new HttpHeaders();
        try {
            carService.addNewCar(carId, model, color, insurance);
        } catch (ObjectAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/create_driver")
    public ResponseEntity<DriverEntity> createDriver(
            @RequestParam String name,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        HttpHeaders headers = new HttpHeaders();
        try {
            driverService.addNewDriver(name, birthDate);
        } catch (WrongAgeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/add_car")
    public ResponseEntity<CarEntity> addCarToDatabase(@RequestBody CarEntity carEntity) {
        HttpHeaders headers = new HttpHeaders();
        if (carEntity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            carService.addCar(carEntity);
        } catch (ObjectAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(carEntity, headers, HttpStatus.CREATED);
    }

    @GetMapping("/get_driver_cars/{driverId}")
    public ResponseEntity<List<CarEntity>> getDriverCars(@PathVariable int driverId) {
        try {
            return new ResponseEntity<>(driverService.getDriverById(driverId).getCars(), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

////    @GetMapping("/driver_by_carId")
////    public ResponseEntity<DriverEntity> getDriver(@RequestParam String carId) {
////        try {
////            return new ResponseEntity<>(driverService.getDriverByCarId(carId), HttpStatus.OK);
////        } catch (ObjectNotFoundException e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////    }
////
////    @GetMapping("/car_by_Id")
////    public ResponseEntity<CarEntity> getCar(@RequestParam String carId) {
////        try {
////            return new ResponseEntity<>(carService.getCarById(carId), HttpStatus.OK);
////        } catch (ObjectNotFoundException e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////    }
//
//    @DeleteMapping("/delete_driver/{driverId}")
//    public ResponseEntity<DriverEntity> deleteDriver(@PathVariable int driverId) {
//        try {
//            driverService.deleteDriverById(driverId);
//        } catch (ObjectNotFoundException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
////    @DeleteMapping("/delete_car/{carId}")
////    public ResponseEntity<CarEntity> deleteCarById(@PathVariable String carId) {
////        try {
////            CarEntity carEntity = carService.getCarById(carId);
////            driverService.getDriverByCarId(carId).getCarEntityList().remove(carEntity);
////            carService.deleteCarById(carId);
////        } catch (ObjectNotFoundException e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////    }
//
//    @PatchMapping("/update_driver")
//    public ResponseEntity<DriverEntity> updateDriver(
//            @RequestParam int driverId,
//            @RequestParam(value = "name", required = false) String newName,
//            @RequestParam(value = "localDate", required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newBirthDate) {
//
//        try {
//            driverService.updateDriverById(driverId, newName, newBirthDate);
//        } catch (ObjectNotFoundException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
////    @PatchMapping("/update_car")
////    public ResponseEntity<CarEntity> updateCar(@RequestParam String carId,
////                                               @RequestParam(required = false) String model,
////                                               @RequestParam(required = false) String color) {
////        try {
////            carService.updateCarById(carId, model, color);
////        } catch (ObjectNotFoundException e) {
////            e.printStackTrace();
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////        return new ResponseEntity<>(HttpStatus.OK);
////    }
//
//    @PatchMapping("/add_car_to_driver")
//    public ResponseEntity<CarEntity> addNewCarToDriver(@RequestParam int driverId, @RequestParam CarEntity carEntity) {
//        try {
//            driverService.addCarToDriverByDriverId(carEntity, driverId);
//        } catch (ObjectNotFoundException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/get_all_drivers")
//    public ResponseEntity<List<DriverEntity>> getAllDrivers() {
//        return new ResponseEntity<>(driverService., HttpStatus.FOUND); // FIND ALL
//    }
//
//    @GetMapping("/get_all_cars")
//    public ResponseEntity<List<CarEntity>> getAllCars() {
//        return new ResponseEntity<>(carService.getAllCarEntities(), HttpStatus.FOUND);
//    }
//
}
