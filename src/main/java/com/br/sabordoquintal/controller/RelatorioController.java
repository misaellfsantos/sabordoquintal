
package com.br.sabordoquintal.controller;

import com.br.sabordoquintal.service.RelatorioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public String abrirRelatorios(Model model) {

        // Faturamento
        model.addAttribute(
                "faturamentoTotal",
                relatorioService.faturamentoTotal()
        );

        model.addAttribute(
                "faturamentoDiario",
                relatorioService.faturamentoDiario()
        );

        model.addAttribute(
                "faturamentoMensal",
                relatorioService.faturamentoMensal()
        );

        // Quantidade de pedidos
        model.addAttribute(
                "totalPedidosEntregues",
                relatorioService.totalPedidosEntregues()
        );

        model.addAttribute(
                "pedidosEntreguesHoje",
                relatorioService.pedidosEntreguesHoje()
        );

        model.addAttribute(
                "pedidosEntreguesMes",
                relatorioService.pedidosEntreguesMes()
        );

        // Histórico de vendas
        model.addAttribute(
                "vendas",
                relatorioService.listarVendas()
        );

        return "admin/relatorios";
    }
}
