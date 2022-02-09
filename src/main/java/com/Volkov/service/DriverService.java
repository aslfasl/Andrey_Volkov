package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.db.repo.DriverRepository;
import com.Volkov.dto.CarDto;
import com.Volkov.dto.Converter;
import com.Volkov.dto.DriverDto;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.ObjectAlreadyExistsException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.exceptions.WrongAgeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DriverService {

    public DriverService(DriverRepository driverRepository, Converter converter) {
        this.driverRepository = driverRepository;
        this.converter = converter;
    }

    private final DriverRepository driverRepository;

    private final Converter converter;
//    @SneakyThrows
//    @PostConstruct
//    public void init() {
//        DriverEntity driver = new DriverEntity();
////        driver.setDateOfBirth(LocalDate.of(2000,1,1));
//        driver.setName("TestDriver3withCars");
//
//        CarEntity car1 = new CarEntity();
//        CarEntity car2 = new CarEntity();
//        CarEntity car3 = new CarEntity();
//
//        car1.setModel("lada");
//        car2.setModel("opel");
//        car3.setModel("jeep");
//
//        System.out.println("********************************************************************");
//        System.out.println(car1 == car2);
//        System.out.println(car1.getCarId());
//        System.out.println(car2.getCarId());
//        System.out.println(car1.equals(car2));
//        System.out.println("********************************************************************");
//
//        driver.addNewCar(car1);
//        driver.addNewCar(car2);
//        driver.addNewCar(car3);
//
//        driverRepository.save(driver);
//    }

    public void addDriver(DriverDto driverDto) throws WrongAgeException, ObjectAlreadyExistsException {
        DriverEntity driverEntity = converter.convertValue(driverDto, DriverEntity.class);
        if (driverRepository.existsDriverByNameAndBirthDate(driverEntity.getName(), driverEntity.getBirthDate())){
            throw new ObjectAlreadyExistsException("Driver already exists");
        }
        if (!ValidationService.driverAgeCheck(driverEntity.getBirthDate())) {
            throw new WrongAgeException("This age is not allowed");
        }
        driverRepository.save(driverEntity);
    }

    public DriverDto addNewDriver(String name, LocalDate birthDate) throws WrongAgeException {
        DriverEntity newDriver = new DriverEntity(name, birthDate);
        if (ValidationService.driverAgeCheck(birthDate)) {
            driverRepository.save(newDriver);
        }
        return converter.convertValue(newDriver, DriverDto.class);
    }

    @Transactional(readOnly = true)
    public DriverDto getDriverById(int driverId) throws ObjectNotFoundException {
        DriverEntity driver = driverRepository.findDriverWithCarsById(driverId);
        if (driver == null) {
            throw new ObjectNotFoundException("Driver not found");
        } else {
            return converter.convertValue(driver, DriverDto.class);
        }
    }

    public void deleteDriverById(int driverId) {
        driverRepository.deleteById(driverId);
    }

    public DriverDto updateDriverById(int driverId, String newName, LocalDate newBirthDate) throws ObjectNotFoundException {
        Optional<DriverEntity> driverEntityOptionalOpt = driverRepository.findById(driverId);
        if (driverEntityOptionalOpt.isPresent()){
            DriverEntity driver = driverEntityOptionalOpt.get();
            if (newName != null) {
                driver.setName(newName);
            }
            if (newBirthDate != null) {
                driver.setBirthDate(newBirthDate);
            }
            driverRepository.save(driver);
            return converter.convertValue(driver, DriverDto.class);
        } else {
            throw new ObjectNotFoundException("Driver not found");
        }
    }

    public DriverDto getDriverByCarRegistrationNumber(String regNumber) {
        return converter.convertValue(driverRepository.findDriverEntityByCarRegistrationNumber(regNumber),
                DriverDto.class);
    }

    public List<DriverDto> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(driverEntity -> converter.convertValue(driverEntity, DriverDto.class))
                .collect(Collectors.toList());
    }

    public void addCarToDriverByDriverId(CarDto carDto, int driverId)
            throws InsuranceException, ObjectAlreadyExistsException {
        DriverEntity driver = driverRepository.findDriverWithCarsById(driverId);
        driver.addNewCar(converter.convertValue(carDto, CarEntity.class));
        driverRepository.save(driver);
    }

    public List<CarDto> getDriverCarsByDriverId(int driverId){
        DriverEntity driver = driverRepository.findDriverWithCarsById(driverId);
        return driver.getCars()
                .stream()
                .map(carEntity -> converter.convertValue(carEntity, CarDto.class))
                .collect(Collectors.toList());
    }
}
