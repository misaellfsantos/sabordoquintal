
package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.Produto;
import com.br.sabordoquintal.repository.ProdutoRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    // Listar todos os produtos
    public List<Produto> listar() {
        return repository.findAll();
    }

    // Buscar produto pelo ID
    public Optional<Produto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Cadastrar ou atualizar produto
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    // Excluir produto
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    // Listar pratos de determinado dia
    public List<Produto> listarPratosDoDia(Integer diaSemana) {
        return repository.findByCategoriaIgnoreCaseAndDiaSemana(
                "PRATO", diaSemana
        );
    }

    // Listar todas as bebidas
    public List<Produto> listarBebidas() {
        return repository.findByCategoriaIgnoreCase("BEBIDA");
    }

    // Cadastrar os produtos iniciais sem duplicar
    public void cadastrarProdutoInicial(
            String nome,
            BigDecimal preco,
            String categoria,
            Integer diaSemana) {

        boolean existe = repository
                .existsByNomeIgnoreCaseAndCategoriaIgnoreCase(
                        nome, categoria
                );

        if (!existe) {
            Produto produto = new Produto(
                    nome,
                    "",
                    preco,
                    categoria,
                    diaSemana
            );

            repository.save(produto);
        }
    }
}
