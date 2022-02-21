package com.Volkov.web;

import com.Volkov.dto.CarDto;
import com.Volkov.service.CarService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class ViewController {

    private CarService carService;

    @GetMapping("/home")
    public String homePage() {
        return "homepage";
    }


    @GetMapping("/add_car")
    public String addCar(Model model) {
        CarDto carDto = new CarDto();
        model.addAttribute("car", carDto);
        List<String> booleanList = Arrays.asList("true", "false");
        model.addAttribute("booleanList", booleanList);
        return "add_car";
    }

    @PostMapping("/add_car")
    public String addCar(@ModelAttribute("car") CarDto carDto) {
        carService.addCar(carDto);
        return "car_added"; // TODO: 16.02.2022  
    }

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("t1");
        String birthday = request.getParameter("t2");

        ModelAndView mv = new ModelAndView();
        mv.setViewName("test");
        mv.addObject("driverName", name);
        mv.addObject("driverBirthday", birthday);

        return mv;
    }

    @GetMapping("/car_list")
    public ModelAndView listCar(ModelAndView modelAndView){
        List<CarDto> cars = carService.getAllCars();
        modelAndView.setViewName("car_list");
        modelAndView.addObject("cars", cars);
        return modelAndView;
    }

    @GetMapping("/add_driver")
    public String addDriver(){
        return "add_driver";
    }
}
