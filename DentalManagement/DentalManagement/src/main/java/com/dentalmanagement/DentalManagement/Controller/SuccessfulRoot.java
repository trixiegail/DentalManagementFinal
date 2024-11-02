package com.dentalmanagement.DentalManagement.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuccessfulRoot {

    @GetMapping("/")
    public String home() {
        return "Congrats potangina mo successful!";
    }
}
