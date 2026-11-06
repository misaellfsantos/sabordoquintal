

package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.conta;
import com.br.sabordoquintal.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    private final ContaRepository repository;

    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }

    // Listar todas as contas
    public List<conta> listar() {
        return repository.findAll();
    }

    // Buscar conta pelo ID
    public Optional<conta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Buscar conta pelo email
    public Optional<conta> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    // Verificar se email já existe
    public boolean emailExiste(String email) {
        return repository.existsByEmail(email);
    }

    // Cadastrar ou atualizar conta
    public conta salvar(conta conta) {
        return repository.save(conta);
    }

    // Excluir conta
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
