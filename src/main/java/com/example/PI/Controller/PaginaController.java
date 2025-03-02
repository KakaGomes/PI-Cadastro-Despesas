package com.example.PI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/diretor")
    public String paginaDiretor() {
        return "diretor";
    }

    @GetMapping("/comprador")
    public String paginaComprador() {
        return "comprador";
    }
    
}
