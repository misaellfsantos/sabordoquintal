
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.model.reserva;
import com.br.sabordoquintal.service.ReservaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/reservas")
    public String reservas(Model model) {
        model.addAttribute("reserva", new reserva());
        return "reserva";
    }

    @PostMapping("/reservas/salvar")
    public String salvar(@ModelAttribute("reserva") reserva novaReserva) {

        if (novaReserva.getStatus() == null ||
                novaReserva.getStatus().isBlank()) {
            novaReserva.setStatus("PENDENTE");
        }

        reservaService.salvar(novaReserva);

        return "redirect:/reservas?sucesso";
    }

    @GetMapping("/admin/reservas")
    public String listarReservas(Model model) {
        model.addAttribute("reservas", reservaService.listar());
        return "admin/reservas";
    }
    @PostMapping("/admin/reservas/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        reservaService.excluir(id);
        return "redirect:/admin/reservas";
    }
}
