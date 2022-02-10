package com.Volkov.web;

import com.Volkov.dto.CarDto;
import com.Volkov.dto.DriverDto;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.exceptions.WrongAgeException;
import com.Volkov.service.DriverService;
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
    public ResponseEntity<DriverDto> addDriverToDatabase(@RequestBody DriverDto driverDto) throws ObjectAlreadyExistsException {
        HttpHeaders headers = new HttpHeaders();
        if (driverDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.addDriver(driverDto);
        return new ResponseEntity<>(driverDto, headers, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<DriverDto> createDriver(
            @RequestParam String name,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        HttpHeaders headers = new HttpHeaders();
            DriverDto driverDto = service.addNewDriver(name, birthDate);
            return new ResponseEntity<>(driverDto, headers, HttpStatus.CREATED);
    }

    @GetMapping("/get_cars/{driverId}")
    public ResponseEntity<List<CarDto>> getDriverCars(@PathVariable int driverId) {
        return new ResponseEntity<>(service.getDriverCarsByDriverId(driverId), HttpStatus.OK);
    }

    @GetMapping("/get_by_regNumber")
    public ResponseEntity<DriverDto> getDriver(@RequestParam String regNumber) {
        return new ResponseEntity<>(service.getDriverByCarRegistrationNumber(regNumber), HttpStatus.OK);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<DriverDto>> getAllDrivers() {
        return new ResponseEntity<>(service.getAllDrivers(), HttpStatus.OK);
    }

    @GetMapping("/get_by_id/{driverId}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable int driverId) {
        try {
            DriverDto driverDto = service.getDriverById(driverId);
            return new ResponseEntity<>(driverDto, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<DriverDto> deleteDriver(@PathVariable int driverId) {
        service.deleteDriverById(driverId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update")
    public ResponseEntity<DriverDto> updateDriver(
            @RequestParam int driverId,
            @RequestParam(value = "name", required = false) String newName,
            @RequestParam(value = "localDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate newBirthDate) {
        try {
            DriverDto driverDto = service.updateDriverById(driverId, newName, newBirthDate);
            return new ResponseEntity<>(driverDto, HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add_car")
    public ResponseEntity<CarDto> addNewCarToDriver(@RequestParam int driverId, @RequestBody CarDto carDto)
            throws ObjectAlreadyExistsException {
        service.addCarToDriverByDriverId(carDto, driverId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}