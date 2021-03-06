package com.volkov.service;

import com.volkov.db.entity.CarEntity;
import com.volkov.db.repo.CarRepository;
import com.volkov.dto.CarDto;
import com.volkov.dto.Converter;
import com.volkov.exceptions.ErrorType;
import com.volkov.exceptions.ObjectAlreadyExistsException;
import com.volkov.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    public CarService(CarRepository carRepository, com.volkov.dto.Converter converter) {
        this.carRepository = carRepository;
        this.converter = converter;
    }

    private final CarRepository carRepository;
    private final Converter converter;


    public CarDto getCarByRegistrationNumber(String regNumber) throws ObjectNotFoundException {
        CarEntity carEntity = carRepository.getCarEntityByRegistrationNumber(regNumber);
        if (carEntity == null) {
            throw new ObjectNotFoundException("Car not found");
        }
        return converter.convertValue(carEntity, CarDto.class);

    }

    public CarDto getCarById(int carId) throws ObjectNotFoundException {
        Optional<CarEntity> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            CarEntity carEntity = carOptional.get();
            return converter.convertValue(carEntity, CarDto.class);
        }
        throw new ObjectNotFoundException("Car not found");
    }


    public CarDto addNewCar(String regNumber, String model, String color, boolean insurance) {
        CarEntity car = new CarEntity(regNumber, model, color, insurance);
        if (carRepository.existsCarByRegistrationNumber(regNumber)) {
            throw new ObjectAlreadyExistsException("Car with the same registration number already exists",
                    ErrorType.ALREADY_EXISTS);
        }
        carRepository.save(car);
        return converter.convertValue(car, CarDto.class);
    }

    public CarDto addCar(CarDto carDto) {
        CarEntity carEntity = converter.convertValue(carDto, CarEntity.class);
        if (!carRepository.existsCarByRegistrationNumber(carEntity.getRegistrationNumber())) {
            carRepository.save(carEntity);
            return carDto;
        } else {
            throw new ObjectAlreadyExistsException("Car with the same registration number already exists",
                    ErrorType.ALREADY_EXISTS);
        }
    }

    public void deleteCarByRegistrationNumber(String regNumber) {
        carRepository.deleteByRegistrationNumber(regNumber);
    }

    public void deleteCarById(int carId) {
        carRepository.deleteById(carId);
    }

    public CarDto updateCarByRegistrationNumber(String regNumber, String newModel, String newColor) {

        CarEntity car = carRepository.getCarEntityByRegistrationNumber(regNumber);

        if (newModel != null) {
            car.setModel(newModel);
        }
        if (newColor != null) {
            car.setColor(newColor);
        }
        carRepository.save(car);
        return converter.convertValue(car, CarDto.class);
    }

    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carEntity -> converter.convertValue(carEntity, CarDto.class))
                .collect(Collectors.toList());
    }

    public Page<CarEntity> findCarsWithSortAndPagination(int offset, int pageSize, String field) {
        return carRepository.findAll(PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.ASC, field)));
    }
}
