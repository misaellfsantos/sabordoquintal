
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.service.ClientesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/clientes")
public class AdminClientesController {

    private final ClientesService clientesService;

    public AdminClientesController(ClientesService clientesService) {
        this.clientesService = clientesService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clientesService.listar());
        return "admin/clientes";
    }

    @PostMapping("/excluir/{id}")
    public String excluirCliente(@PathVariable Long id) {
        clientesService.excluir(id);
        return "redirect:/admin/clientes";
    }
}
