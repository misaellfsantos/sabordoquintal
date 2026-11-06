
package com.br.sabordoquintal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // Abrir página de login do administrador
    @GetMapping("/admin")
    public String admin() {
        return "login";
    }

    // Abrir painel administrativo
    @GetMapping("/admin/painel")
    public String painel() {
        return "admin";
    }

}
