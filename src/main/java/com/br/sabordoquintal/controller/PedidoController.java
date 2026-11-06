
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.pedido;
import com.br.sabordoquintal.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<pedido> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public pedido buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public pedido cadastrar(@RequestBody pedido pedido) {
        return service.salvar(pedido);
    }

    @PutMapping("/{id}")
    public pedido atualizar(
            @PathVariable Long id,
            @RequestBody pedido pedido) {

        pedido.setId(id);
        return service.salvar(pedido);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
