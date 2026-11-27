
package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.reserva;
import com.br.sabordoquintal.repository.ReservaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<reserva> listar() {
        return repository.findAll();
    }

    public reserva salvar(reserva reserva) {
        return repository.save(reserva);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<reserva> buscarPorTelefone(String telefone) {
        return repository.findByTelefone(telefone);
    }
}
