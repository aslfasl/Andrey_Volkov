package com.Volkov.service;

import com.Volkov.db.entity.CarEntity;
import com.Volkov.db.entity.DriverEntity;
import com.Volkov.db.repo.DriverRepository;
import com.Volkov.exceptions.InsuranceException;
import com.Volkov.exceptions.ObjectNotFoundException;
import com.Volkov.exceptions.WrongAgeException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DriverService {

    private final DriverRepository driverRepository;

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


    public void addDriver(DriverEntity newDriver) throws WrongAgeException {
        if (!driverRepository.existsDriverByNameAndBirthDate(newDriver.getName(), newDriver.getBirthDate())
                && ValidationService.driverAgeCheck(newDriver.getBirthDate())) {
            driverRepository.save(newDriver);
        }
    }

    public void addNewDriver(String name, LocalDate birthDate) throws WrongAgeException {
        DriverEntity newDriver = new DriverEntity(name, birthDate);
        if (ValidationService.driverAgeCheck(birthDate)) {
            driverRepository.save(newDriver);
        }
    }

    @Transactional(readOnly = true)
    public DriverEntity getDriverById(int driverId) throws ObjectNotFoundException {
        DriverEntity driver = driverRepository.findDriverWithCarsById(driverId);
        if (driver == null) {
            throw new ObjectNotFoundException("Driver not found");
        } else {
            return driver;
        }
    }

    public void deleteDriverById(int driverId) {
        driverRepository.deleteById(driverId);
    }

    public void updateDriverById(int driverId, String newName, LocalDate newBirthDate) throws ObjectNotFoundException {
        DriverEntity driver = getDriverById(driverId);

        if (newName != null) {
            driver.setName(newName);
        }
        if (newBirthDate != null) {
            driver.setBirthDate(newBirthDate);
        }

        driverRepository.save(driver);
    }

    public DriverEntity getDriverByCarReistrationNumber(String regNumber) {
        return driverRepository.findDriverEntityByCarRegistrationNumber(regNumber);
    }

    public void addCarToDriverByDriverId(CarEntity car, int driverId)
            throws ObjectNotFoundException, InsuranceException {
        DriverEntity driver = getDriverById(driverId);
        List<CarEntity> driverCars = driver.getCars();

        if (!driverCars.contains(car)) {

            driver.addNewCar(car);
        }

        driverRepository.save(driver);
    }
}
