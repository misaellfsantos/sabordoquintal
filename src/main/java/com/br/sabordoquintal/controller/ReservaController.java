
package com.br.sabordoquintal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservaController {

    // Abrir página de reservas
    @GetMapping("/reserva")
    public String reserva() {
        return "reserva";
    }

}
