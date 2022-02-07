package com.Volkov.db.repo;

import com.Volkov.db.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

    boolean existsCarByRegistrationNumber(String registrationNumber);

    @Transactional
    void deleteByRegistrationNumber(String registrationNumber);

    CarEntity getCarEntityByRegistrationNumber(String registrationNumber);


//    @Modifying
//    @Query("UPDATE CarEntity c SET c.color = ?2, c.model = ?3 WHERE c.registrationNumber = ?1")
//    void setCarParametersByRegistrationNumber(String registrationNumber, String color, String model);
}
