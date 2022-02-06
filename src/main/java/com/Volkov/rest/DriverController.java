package com.Volkov.rest;

import com.Volkov.db.entity.DriverEntity;
import com.Volkov.dto.CarDto;
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
import java.util.List;

@RestController
@RequestMapping("drivers")
@AllArgsConstructor
public class DriverController {

    private DriverService service;


    @PostMapping("/add")
    public ResponseEntity<DriverDto> addDriverToDatabase(@RequestBody DriverDto driverDto) {
        HttpHeaders headers = new HttpHeaders();
        if (driverDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            service.addDriver(driverDto);
            return new ResponseEntity<>(driverDto, headers, HttpStatus.CREATED);
        } catch (WrongAgeException | ObjectAlreadyExistsException e) {
            return new ResponseEntity<>(driverDto, headers, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<DriverDto> createDriver(
            @RequestParam String name,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        HttpHeaders headers = new HttpHeaders();
        try {
            DriverDto driverDto = service.addNewDriver(name, birthDate);
            return new ResponseEntity<>(driverDto, headers, HttpStatus.CREATED);
        } catch (WrongAgeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_cars/{driverId}")
    public ResponseEntity<List<CarDto>> getDriverCars(@PathVariable int driverId) {
        return new ResponseEntity<>(service.getDriverCarsByDriverId(driverId), HttpStatus.OK);
    }

    @GetMapping("/get_by_regNumber")
    public ResponseEntity<DriverDto> getDriver(@RequestParam String regNumber) {
        return new ResponseEntity<>(service.getDriverByCarRegistrationNumber(regNumber), HttpStatus.OK);
    }

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
