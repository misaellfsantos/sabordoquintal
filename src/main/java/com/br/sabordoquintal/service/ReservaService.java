
package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.reserva;
import com.br.sabordoquintal.repository.ReservaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Listar todas as reservas
    public List<reserva> listar() {
        return reservaRepository.findAll();
    }

    // Buscar reserva pelo ID
    public Optional<reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    // Salvar reserva
    public reserva salvar(reserva novaReserva) {
        return reservaRepository.save(novaReserva);
    }

    // Excluir reserva
    public void excluir(Long id) {
        reservaRepository.deleteById(id);
    }

    // Verificar se existe reserva
    public boolean existe(Long id) {
        return reservaRepository.existsById(id);
    }
}
