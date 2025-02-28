package com.example.PI.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.PI.Entities.FornecedorEntities;
import com.example.PI.Repositories.FornecedorRepository;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public List<FornecedorEntities> getAllFornecedores() {
        return fornecedorRepository.findAll();
    }

    @PostMapping
    public FornecedorEntities createFornecedor(@RequestBody FornecedorEntities fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    @GetMapping("/{id}")
    public FornecedorEntities getFornecedorById(@PathVariable Long id) {
        return fornecedorRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public FornecedorEntities updateFornecedor(@PathVariable Long id,
            @RequestBody FornecedorEntities fornecedorDetails) {
        FornecedorEntities fornecedor = fornecedorRepository.findById(id).orElse(null);
        if (fornecedor != null) {
            fornecedor.setNome(fornecedorDetails.getNome());
            fornecedor.setCnpj(fornecedorDetails.getCnpj());
            return fornecedorRepository.save(fornecedor);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
    }
}
