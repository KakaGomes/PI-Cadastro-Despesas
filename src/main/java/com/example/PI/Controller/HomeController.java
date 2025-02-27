package com.example.PI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "index"; // O Spring buscará "index.html" em src/main/resources/templates/
    }
}
