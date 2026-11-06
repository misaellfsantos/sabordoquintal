
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

    public List<conta> listar() {
        return repository.findAll();
    }

    public Optional<conta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<conta> buscarPorEmail(String email) {
        return repository.findByEmail(email);
    }

    public conta salvar(conta conta) {
        return repository.save(conta);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
