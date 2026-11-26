package com.br.sabordoquintal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class LocalizacaoController {

@GetMapping("/localizacao")
    public String abrirLocalizacao() {
        return "localizacao";
    }
}
