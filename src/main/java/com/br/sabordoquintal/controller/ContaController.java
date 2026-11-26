
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.conta;
import com.br.sabordoquintal.repository.ContaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ContaController {

    private final ContaRepository repository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public ContaController(ContaRepository repository) {
        this.repository = repository;
    }

    // CADASTRO
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(
            @RequestBody Map<String, String> dados) {

        String nome = dados.get("nome");
        String email = dados.get("email");
        String telefone = dados.get("telefone");
        String senha = dados.get("senha");

        if (nome == null || email == null || senha == null ||
                nome.isBlank() || email.isBlank() || senha.isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Preencha todos os campos obrigatórios."));
        }

        email = email.trim().toLowerCase();

        if (repository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", "Este e-mail já está cadastrado."));
        }

        conta novaConta = new conta();
        novaConta.setNome(nome.trim());
        novaConta.setEmail(email);
        novaConta.setTelefone(telefone);
        novaConta.setSenha(passwordEncoder.encode(senha));

        repository.save(novaConta);

        return ResponseEntity.ok(
                Map.of("mensagem", "Cadastro realizado com sucesso!")
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> dados) {

        String email = dados.get("email");
        String senha = dados.get("senha");

        if (email == null || senha == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("mensagem", "Informe o e-mail e a senha."));
        }

        conta usuario = repository.findByEmail(
                email.trim().toLowerCase()
        ).orElse(null);

        if (usuario == null ||
                !passwordEncoder.matches(senha, usuario.getSenha())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensagem", "E-mail ou senha incorretos."));
        }

        return ResponseEntity.ok(Map.of(
                "mensagem", "Login realizado com sucesso!",
                "nome", usuario.getNome(),
                "email", usuario.getEmail(),
                "telefone", usuario.getTelefone() == null
                        ? "" : usuario.getTelefone()
        ));
    }
}
