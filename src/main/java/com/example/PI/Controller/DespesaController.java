package com.example.PI.Controller;

import com.example.PI.Entities.DespesaEntities;
import com.example.PI.Repositories.DespesaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {
    private final DespesaRepository repository;

    public DespesaController(DespesaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<DespesaEntities> listarDespesas() {
        return repository.findAll();
    }

    @PostMapping
    public DespesaEntities adicionarDespesa(@RequestBody DespesaEntities despesa) {
        return repository.save(despesa);
    }

    @DeleteMapping("/{id}")
    public void removerDespesa(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
