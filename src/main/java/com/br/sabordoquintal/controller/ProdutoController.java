
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.produto;
import com.br.sabordoquintal.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public List<produto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public produto buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public produto cadastrar(@RequestBody produto produto) {
        return service.salvar(produto);
    }

    @PutMapping("/{id}")
    public produto atualizar(
            @PathVariable Long id,
            @RequestBody produto produto) {

        produto.setId(id);
        return service.salvar(produto);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
