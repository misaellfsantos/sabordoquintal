
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.conta;
import com.br.sabordoquintal.service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service) {
        this.service = service;
    }

    @GetMapping
    public List<conta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public conta buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public conta cadastrar(@RequestBody conta conta) {
        return service.salvar(conta);
    }

    @PutMapping("/{id}")
    public conta atualizar(
            @PathVariable Long id,
            @RequestBody conta conta) {

        conta.setId(id);
        return service.salvar(conta);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
