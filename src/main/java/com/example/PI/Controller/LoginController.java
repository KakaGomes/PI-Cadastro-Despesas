package com.example.PI.Controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import com.example.PI.Entities.Usuario;
import com.example.PI.Repositories.UsuarioRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
public ResponseEntity<?> autenticarUsuario(@RequestBody Usuario usuario, HttpSession session) {
    Usuario user = usuarioRepository.findByUsuarioAndSenha(usuario.getUsuario(), usuario.getSenha());

    if (user != null) {
        session.setAttribute("usuarioLogado", user);  // Armazena o usuário na sessão
        return ResponseEntity.ok(Collections.singletonMap("tipo", user.getTipo()));
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
    }
}
}
