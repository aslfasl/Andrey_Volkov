package com.volkov.db.repo;

import com.volkov.db.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {

    boolean existsCarByRegistrationNumber(String registrationNumber);

    @Transactional
    void deleteByRegistrationNumber(String registrationNumber);

    CarEntity getCarEntityByRegistrationNumber(String registrationNumber);

}
