package com.br.sabordoquintal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(Model model) {

        model.addAttribute("totalProdutos", 0);
        model.addAttribute("totalPedidos", 0);
        model.addAttribute("totalReservas", 0);
        model.addAttribute("vendasDia", "0,00");

        return "admin";
    }
}