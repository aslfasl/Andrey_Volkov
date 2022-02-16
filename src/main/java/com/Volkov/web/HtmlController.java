package com.Volkov.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class HtmlController {


    @GetMapping("/hello/{name}")
    public String hello(Model model, @PathVariable String name) {
        log.debug(name);
        model.addAttribute("name", name);
        return "hello";
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

    @GetMapping("/submit")
    public String submit(){
        return "test1";
    }
}
