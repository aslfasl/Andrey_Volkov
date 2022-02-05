package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.repo.CarRepository;
import com.Volkov.dto.CarDto;
import com.Volkov.dto.Converter;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @SneakyThrows
    @PostConstruct
    public void init() {
//        CarEntity car1 = new CarEntity();
//        car1.setModel("bmw");
//        DriverEntity driver = new DriverEntity();
//        driver.setName("TestDriverForOneCarSave");
//        car1.setOwner(driver);
//
//        carRepository.save(car1);


//        System.out.println("GET CAR BY ID METHOD:");
//        CarEntity car = getCarById(1);
//        System.out.println(car);

    }

    public CarDto getCarByRegistrationNumber(String regNumber) throws ObjectNotFoundException {
        CarEntity carEntity = carRepository.getCarEntityByRegistrationNumber(regNumber);
        if (carEntity == null) {
            throw new ObjectNotFoundException("Car not found");
        }
        return Converter.convertValue(carEntity, CarDto.class);

    }

    public CarDto getCarById(int carId) throws ObjectNotFoundException {
        Optional<CarEntity> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            CarEntity carEntity = carOptional.get();
            return Converter.convertValue(carEntity, CarDto.class);
        }
        throw new ObjectNotFoundException("Car not found");
    }


    public void addNewCar(String regNumber, String model, String color, boolean insurance) throws ObjectAlreadyExistsException {
        CarEntity car = new CarEntity(regNumber, model, color, insurance);
        if (!carRepository.existsCarByRegistrationNumber(regNumber)) {
            carRepository.save(car);
        } else {
            throw new ObjectAlreadyExistsException("Car with the same registration number already exists");
        }
    }

    public void addCar(CarDto carDto) throws ObjectAlreadyExistsException {
        CarEntity carEntity = Converter.convertValue(carDto, CarEntity.class);
        if (!carRepository.existsCarByRegistrationNumber(carEntity.getRegistrationNumber())) {
            carRepository.save(carEntity);
        } else {
            throw new ObjectAlreadyExistsException("Car with the same registration number already exists");
        }
    }

    public void deleteCarByRegistrationNumber(String regNumber) {
        carRepository.deleteByRegistrationNumber(regNumber);
    }

    public void deleteCarById(int carId) {
        carRepository.deleteById(carId);
    }

    public void updateCarByRegistrationNumber(String regNumber, String newModel, String newColor) {

        CarEntity car = carRepository.getCarEntityByRegistrationNumber(regNumber);

        if (newModel != null) {
            car.setModel(newModel);
        }
        if (newColor != null) {
            car.setColor(newColor);
        }
        carRepository.save(car);
    }

    public Page<CarEntity> findCarsWithSortAndPagination(int offset, int pageSize, String field) {
        Page<CarEntity> cars = carRepository.findAll(PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.ASC, field)));
        return cars;
    }
}