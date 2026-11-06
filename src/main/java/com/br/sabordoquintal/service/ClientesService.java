
package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.clientes;
import com.br.sabordoquintal.repository.ClientesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientesService {

    private final ClientesRepository repository;

    public ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }

    public List<clientes> listar() {
        return repository.findAll();
    }

    public Optional<clientes> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public clientes salvar(clientes cliente) {
        return repository.save(cliente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
