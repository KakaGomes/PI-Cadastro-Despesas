package com.example.PI.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.PI.Entities.FornecedorEntities;
import com.example.PI.Repositories.FornecedorRepository;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin/diretor")
public class DiretorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public String carregarPaginaDiretor(Model model) {
        List<FornecedorEntities> fornecedores = fornecedorRepository.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "diretor"; // Nome do arquivo HTML (diretor.html)
    }

    @GetMapping("/teste-fornecedores")
@ResponseBody
public List<FornecedorEntities> testeFornecedores() {
    return fornecedorRepository.findAll();
}

}
