package com.example.PI.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.PI.Entities.Usuario;
import com.example.PI.Repositories.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void atualizarSenhasParaCriptografadas() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario user : usuarios) {
            if (!user.getSenha().startsWith("$2a$")) { // Verifica se a senha já está criptografada
                user.setSenha(passwordEncoder.encode(user.getSenha())); // Criptografa a senha
                usuarioRepository.save(user); // Salva a senha atualizada no banco
            }
        }
    }
}
