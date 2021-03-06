package com.volkov.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table()
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    @JsonIgnore
    private int carId;

    @Column()
    private String registrationNumber;

    @Column()
    private String model;

    @Column()
    private String color;

    @Column()
    private boolean insurance;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    @JsonIgnore
    private DriverEntity owner;

    public CarEntity(String registrationNumber, String model, String color, boolean insurance) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.color = color;
        this.insurance = insurance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarEntity carEntity = (CarEntity) o;

        return this.registrationNumber.equalsIgnoreCase(carEntity.getRegistrationNumber());
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "carId=" + carId +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", insurance=" + insurance +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }
}
