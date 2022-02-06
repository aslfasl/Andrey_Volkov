package com.Volkov.rest;

import com.Volkov.db.entity.DriverEntity;
import com.Volkov.dto.DriverDto;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.WrongAgeException;
import com.Volkov.service.DriverService;
import com.Volkov.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("controller")
@AllArgsConstructor
public class DriverController {

    private DriverService driverService;
    private CarService carService;

    @PostMapping("/add_driver")
    public ResponseEntity<DriverDto> addDriverToDatabase(@RequestBody DriverDto driverDto) {
        HttpHeaders headers = new HttpHeaders();
        if (driverDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            driverService.addDriver(driverDto);
        } catch (WrongAgeException | ObjectAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(driverDto, headers, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(driverDto, headers, HttpStatus.CREATED);
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

//    @GetMapping("/get_driver_cars/{driverId}")
//    public ResponseEntity<List<CarDto>> getDriverCars(@PathVariable int driverId) {
//        try {
//            return new ResponseEntity<>(driverService.getDriverById(driverId).getCars(), HttpStatus.OK);
//        } catch (ObjectNotFoundException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

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

//
}
