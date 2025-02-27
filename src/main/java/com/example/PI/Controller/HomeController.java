package com.example.PI.Controller;

<<<<<<< HEAD
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "index"; // O Spring buscará "index.html" em src/main/resources/templates/
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<?> redirecionarParaIndex() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/index.html")
                .build();
>>>>>>> 9f61c23230205ac84b6b0553ad11ef68c8534566
    }
}
