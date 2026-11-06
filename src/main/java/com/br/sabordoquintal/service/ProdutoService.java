
package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.produto;
import com.br.sabordoquintal.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<produto> listar() {
        return repository.findAll();
    }

    public Optional<produto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public produto salvar(produto produto) {
        return repository.save(produto);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
