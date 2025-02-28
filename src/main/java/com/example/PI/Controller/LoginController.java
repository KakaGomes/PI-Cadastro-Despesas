package com.example.PI.Controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PI.Entities.Usuario;
import com.example.PI.Repositories.UsuarioRepository;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> autenticarUsuario(@RequestBody Usuario usuario) {
        Usuario user = usuarioRepository.findByUsuarioAndSenha(usuario.getUsuario(), usuario.getSenha());

        if (user != null) {
            return ResponseEntity.ok(Collections.singletonMap("tipo", user.getTipo()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }
}
