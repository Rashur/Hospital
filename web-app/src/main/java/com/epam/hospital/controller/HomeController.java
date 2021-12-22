package com.epam.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String redirectToDefaultPage() {
        return "redirect:/patients";
    }
}
