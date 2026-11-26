
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.pedido;
import com.br.sabordoquintal.service.PedidoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Abrir página de pedidos do cliente
    @GetMapping("/pedido")
    public String abrirPedido() {
        return "pedido";
    }

    // Listar pedidos no painel administrativo
    @GetMapping("/admin/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listar());
        return "admin/pedidos";
    }

    // Excluir pedido
    @PostMapping("/admin/pedidos/excluir/{id}")
    public String excluirPedido(@PathVariable Long id) {
        pedidoService.excluir(id);
        return "redirect:/admin/pedidos";
    }

    // Atualizar status do pedido
    @PostMapping("/admin/pedidos/status/{id}")
    public String atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        pedido pedidoExistente = pedidoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        pedidoExistente.setStatus(status);
        pedidoService.salvar(pedidoExistente);

        return "redirect:/admin/pedidos";
    }
}
