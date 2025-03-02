package com.example.PI.Controller;

import com.example.PI.Entities.Usuario;
import com.example.PI.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
        }

     

    // ✅ NOVO MÉTODO PARA SUPORTAR FORMULÁRIOS HTML
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView criarUsuarioViaFormulario(RedirectAttributes attributes,
            @RequestParam String nome,
            @RequestParam String usuario,
            @RequestParam String senha,
            @RequestParam String tipo) {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setUsuario(usuario);
        novoUsuario.setSenha(passwordEncoder.encode(senha)); // Criptografar senha
        novoUsuario.setTipo(tipo);

        usuarioRepository.save(novoUsuario);

        attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
    
        return new RedirectView("/diretor"); // Redireciona para a rota já existente no PaginaController
    }

    @GetMapping("/{id}")
    public Optional<Usuario> buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setUsuario(usuarioDetails.getUsuario());
            usuario.setSenha(passwordEncoder.encode(usuarioDetails.getSenha()));
            usuario.setTipo(usuarioDetails.getTipo());
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
