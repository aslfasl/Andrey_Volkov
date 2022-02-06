package com.Volkov.rest;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.dto.CarDto;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
@AllArgsConstructor
public class CarController {

    private CarService service;

    @PostMapping("/create")
    public ResponseEntity<CarDto> createCar(
            @RequestParam String regNumber,
            @RequestParam String model,
            @RequestParam String color,
            @RequestParam(required = false, defaultValue = "false") boolean insurance) {
        HttpHeaders headers = new HttpHeaders();
        try {
            CarDto carDto = service.addNewCar(regNumber, model, color, insurance);
            return new ResponseEntity<>(carDto, headers, HttpStatus.CREATED);
        } catch (ObjectAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CarDto> addCarToDatabase(@RequestBody CarDto carDto) {
        HttpHeaders headers = new HttpHeaders();
        if (carDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            service.addCar(carDto);
            return new ResponseEntity<>(carDto, headers, HttpStatus.CREATED);
        } catch (ObjectAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find_by_id")
    public ResponseEntity<CarDto> getCar(@RequestParam int carId) {
        try {
            return new ResponseEntity<>(service.getCarById(carId), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/find_by_regNumber")
    public ResponseEntity<CarDto> getCar(@RequestParam String regNumber) {
        try {
            return new ResponseEntity<>(service.getCarByRegistrationNumber(regNumber), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return new ResponseEntity<>(service.getAllCars(), HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<CarDto> updateCar(@RequestParam String regNumber,
                                            @RequestParam(required = false) String model,
                                            @RequestParam(required = false) String color) {
        CarDto carDto = service.updateCarByRegistrationNumber(regNumber, model, color);
        return new ResponseEntity<>(carDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete_id/{carId}")
    public ResponseEntity<CarDto> deleteCarById(@PathVariable int carId) {
        service.deleteCarById(carId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete_regNumber/{regNumber}")
    public ResponseEntity<CarDto> deleteCarByRegNumber(@PathVariable String regNumber) {
        service.deleteCarByRegistrationNumber(regNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
