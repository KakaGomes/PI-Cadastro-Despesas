package com.example.PI.Controller;

import com.example.PI.Entities.FornecedorEntities;
import com.example.PI.Repositories.FornecedorRepository;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping
    public List<FornecedorEntities> listarFornecedores() {
        return fornecedorRepository.findAll();
    }

    @GetMapping("/novo")
    public String mostrarFormularioCompra(Model model) {
        List<FornecedorEntities> fornecedores = fornecedorRepository.findAll();
        model.addAttribute("fornecedores", fornecedores);
        return "cadastro_compras"; // Nome do arquivo HTML
    }
    

    @PostMapping
    public ResponseEntity<Map<String, String>> cadastrarFornecedor(@RequestParam String nome, @RequestParam String cnpj) {
        // Remove pontos, barras e traços do CPF/CNPJ
        String cnpjFormatado = cnpj.replaceAll("[^0-9]", "");
    
        // Verifica se já existe no banco
        Optional<FornecedorEntities> fornecedorExistente = fornecedorRepository.findByCnpj(cnpjFormatado);
        Map<String, String> resposta = new HashMap<>();
    
        if (fornecedorExistente.isPresent()) {
            resposta.put("status", "erro");
            resposta.put("mensagem", "Erro: CPF/CNPJ já cadastrado!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta); // Retorna JSON para o frontend
        }
    
        // Criando novo fornecedor
        FornecedorEntities novoFornecedor = new FornecedorEntities();
        novoFornecedor.setNome(nome);
        novoFornecedor.setCnpj(cnpjFormatado);
        fornecedorRepository.save(novoFornecedor);
    
        resposta.put("status", "sucesso");
        resposta.put("mensagem", "Fornecedor cadastrado com sucesso!");
        return ResponseEntity.ok(resposta); // Retorna JSON para o frontend
    }
    
    
   

    @GetMapping("/{id}")
    public Optional<FornecedorEntities> buscarFornecedorPorId(@PathVariable Long id) {
        return fornecedorRepository.findById(id);
    }

    @PutMapping("/{id}")
    public FornecedorEntities atualizarFornecedor(@PathVariable Long id, @RequestBody FornecedorEntities fornecedorDetails) {
        return fornecedorRepository.findById(id).map(fornecedor -> {
            fornecedor.setNome(fornecedorDetails.getNome());
            fornecedor.setCnpj(fornecedorDetails.getCnpj());
            return fornecedorRepository.save(fornecedor);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletarFornecedor(@PathVariable Long id) {
        fornecedorRepository.deleteById(id);
    }

    @GetMapping("/buscar")
@ResponseBody
public List<Map<String, Object>> buscarFornecedores(@RequestParam(required = false) String termo) {
    List<Object[]> resultados = fornecedorRepository.buscarPorNomeOuCnpjComTotais(termo);
    
    List<Map<String, Object>> resposta = new ArrayList<>();

    for (Object[] resultado : resultados) {
        FornecedorEntities fornecedor = (FornecedorEntities) resultado[0];
        BigDecimal totalAprovado = (BigDecimal) resultado[1]; // Converte corretamente
        BigDecimal totalPendente = (BigDecimal) resultado[2]; // Converte corretamente

        Map<String, Object> mapa = new HashMap<>();
        mapa.put("id", fornecedor.getId());
        mapa.put("nome", fornecedor.getNome());
        mapa.put("cnpj", fornecedor.getCnpj());
        mapa.put("totalAprovado", totalAprovado != null ? totalAprovado.doubleValue() : 0.0);
        mapa.put("totalPendente", totalPendente != null ? totalPendente.doubleValue() : 0.0);

        resposta.add(mapa);
    }

    return resposta;
}


}
