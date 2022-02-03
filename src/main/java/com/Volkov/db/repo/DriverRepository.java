package com.Volkov.db.repo;

import com.Volkov.db.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;


@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
    boolean existsDriverByNameAndBirthDate(String name, LocalDate birthdate);

    @Query(value = "SELECT * FROM driver_entity de\n" +
            "JOIN car_entity ce ON de.driver_id=ce.owner_id\n" +
            "WHERE ce.registration_number = ?1",
            nativeQuery = true)
    DriverEntity findDriverEntityByCarRegistrationNumber(String regNumber);


//    @Query(value = "SELECT * FROM driver_entity de\n" +
//            "LEFT JOIN car_entity ce ON de.driver_id=ce.owner_id\n" +
//            "WHERE de.driver_id = ?1", nativeQuery = true)
//    DriverEntity selectDriverById(int driverId);

    @Query(value = "SELECT de FROM DriverEntity de LEFT JOIN FETCH de.cars WHERE de.driverId = ?1")
    DriverEntity findDriverWithCarsById(int driverId);

    @Query(value = "SELECT de FROM DriverEntity de LEFT JOIN FETCH de.cars ")
    List<DriverEntity> findAllDriversWithCars();
}
