package com.example.PI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/diretor")
    public String paginaDiretor() {
        return "diretor"; // Nome do arquivo dentro de templates/diretor.html
    }

    @GetMapping("/comprador")
    public String paginaComprador() {
        return "comprador"; // Nome do arquivo dentro de templates/comprador.html
    }
}
