package com.example.PI.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.PI.Entities.Usuario;
import com.example.PI.Repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login") // Garante que está correto
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody Map<String, String> dadosLogin) {
        String login = dadosLogin.get("usuario"); // Certifique-se de que o JSON está correto
        String senha = dadosLogin.get("senha");

        Usuario user = usuarioRepository.findByLogin(login); // Certifique-se de que o método está correto

        if (user != null && passwordEncoder.matches(senha, user.getSenha())) { // Se a senha estiver criptografada
            Map<String, String> resposta = new HashMap<>();
            resposta.put("tipo", user.getTipo()); // Retorna o tipo do usuário
            return ResponseEntity.ok(resposta);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }
}
