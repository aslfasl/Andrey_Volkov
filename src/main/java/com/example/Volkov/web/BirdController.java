package com.example.Volkov.web;

import com.example.Volkov.dto.Bird;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("birds")
public class BirdController {
    Bird myBird = new Bird();
    List<Bird> flock = new ArrayList<>();

    @PostMapping("/myBird")
    public String addBird(@RequestBody Bird bird) {
        flock.add(bird);
        return "added";
    }

    @GetMapping("/myBird")
    public Bird getBird() {
        return myBird;
    }

     @GetMapping("/showAllBirds")
     public List<Bird> getAllBirds() {
        return flock;
     }

    @GetMapping("/kill")
    public String killBird() {
        return "It is done, master.";
    }



}

