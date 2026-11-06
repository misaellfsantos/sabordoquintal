
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.clientes;
import com.br.sabordoquintal.service.ClientesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    private final ClientesService service;

    public ClientesController(ClientesService service) {
        this.service = service;
    }

    @GetMapping
    public List<clientes> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public clientes buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public clientes cadastrar(@RequestBody clientes cliente) {
        return service.salvar(cliente);
    }

    @PutMapping("/{id}")
    public clientes atualizar(
            @PathVariable Long id,
            @RequestBody clientes cliente) {

        cliente.setId(id);
        return service.salvar(cliente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
