package com.example.Volkov.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CarTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testEquals() {
        //given
        Car car1 = new Car("o1", "ferrari", "red", true);
        Car car2 = new Car("o1", "jeep", "black", false);
        //when
        //then
        assertEquals(car1, car2);
    }
}