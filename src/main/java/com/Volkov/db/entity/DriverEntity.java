package com.Volkov.db.entity;

import com.Volkov.exceptions.InsuranceException;

import com.Volkov.service.ValidationService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@Table()
public class DriverEntity {

    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int driverId;

    @Column()
    private String name;

    @Column()
    private LocalDate birthDate;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "owner")
    private List<CarEntity> cars = new ArrayList<>();

    public DriverEntity(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public void addNewCar(CarEntity car) throws InsuranceException {
        if (!cars.contains(car) && ValidationService.insuranceCheck(car)) {
            cars.add(car);
            car.setOwner(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverEntity driver = (DriverEntity) o;
        return name.equals(driver.name) && birthDate.equals(driver.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate);
    }
}
