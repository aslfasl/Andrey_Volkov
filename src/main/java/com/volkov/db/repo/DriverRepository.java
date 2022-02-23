package com.volkov.db.repo;

import com.volkov.db.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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

    @Query(value = "SELECT de FROM DriverEntity de LEFT JOIN FETCH de.cars WHERE de.driverId = ?1")
    DriverEntity findDriverWithCarsById(int driverId);

    @Query(value = "SELECT de FROM DriverEntity de LEFT JOIN FETCH de.cars ")
    List<DriverEntity> findAllDriversWithCars();

    @Transactional
    @Modifying
    @Query(value = "ALTER SEQUENCE public.driver_entity_driver_id_seq RESTART WITH 1;",
            nativeQuery = true)
    void resetSequence();
}
