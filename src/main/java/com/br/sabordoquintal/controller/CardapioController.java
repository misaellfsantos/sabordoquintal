package com.br.sabordoquintal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class CardapioController {

    @GetMapping("/cardapio")
    public String abrirCardapio() {
        return "cardapio";
    }
}
