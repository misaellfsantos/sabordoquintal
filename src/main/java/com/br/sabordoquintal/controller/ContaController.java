
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

    // =========================
    // CADASTRO DE CONTA
    // =========================

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(
            @RequestBody Map<String, String> dados) {

        String nome = dados.get("nome");
        String email = dados.get("email");
        String telefone = dados.get("telefone");
        String senha = dados.get("senha");

        // Validar campos obrigatórios
        if (nome == null || email == null || senha == null ||
                nome.isBlank() || email.isBlank() || senha.isBlank()) {

            return ResponseEntity.badRequest().body(
                    Map.of("mensagem",
                            "Preencha todos os campos obrigatórios.")
            );
        }

        nome = nome.trim();
        email = email.trim().toLowerCase();

        // Validar tamanho mínimo da senha
        if (senha.length() < 6) {
            return ResponseEntity.badRequest().body(
                    Map.of("mensagem",
                            "A senha deve possuir pelo menos 6 caracteres.")
            );
        }

        // Verificar se o e-mail já está cadastrado
        if (repository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("mensagem",
                            "Este e-mail já está cadastrado.")
            );
        }

        // Criar nova conta
        conta novaConta = new conta();

        novaConta.setNome(nome);
        novaConta.setEmail(email);
        novaConta.setTelefone(
                telefone == null ? "" : telefone.trim()
        );

        // Criptografar senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(senha);

        novaConta.setSenha(senhaCriptografada);

        // Salvar no banco de dados
        repository.save(novaConta);

        return ResponseEntity.ok(
                Map.of("mensagem",
                        "Cadastro realizado com sucesso!")
        );
    }

    // =========================
    // LOGIN DE CONTA
    // =========================

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> dados) {

        String email = dados.get("email");
        String senha = dados.get("senha");

        // Validar campos
        if (email == null || senha == null ||
                email.isBlank() || senha.isEmpty()) {

            return ResponseEntity.badRequest().body(
                    Map.of("mensagem",
                            "Informe o e-mail e a senha.")
            );
        }

        email = email.trim().toLowerCase();

        // Buscar conta pelo e-mail
        conta usuario = repository.findByEmail(email)
                .orElse(null);

        // Verificar se a conta existe
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("mensagem",
                            "E-mail ou senha incorretos.")
            );
        }

        // Verificar se existe uma senha cadastrada
        if (usuario.getSenha() == null ||
                usuario.getSenha().isBlank()) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("mensagem",
                            "E-mail ou senha incorretos.")
            );
        }

        // Comparar senha digitada com a senha criptografada
        boolean senhaCorreta = passwordEncoder.matches(
                senha,
                usuario.getSenha()
        );

        // Impedir acesso caso a senha esteja incorreta
        if (!senhaCorreta) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("mensagem",
                            "E-mail ou senha incorretos.")
            );
        }

        // Login realizado com sucesso
        return ResponseEntity.ok(
                Map.of(
                        "mensagem", "Login realizado com sucesso!",
                        "nome", usuario.getNome(),
                        "email", usuario.getEmail(),
                        "telefone", usuario.getTelefone() == null
                                ? ""
                                : usuario.getTelefone()
                )
        );
    }
}
