
package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.pedido;
import com.br.sabordoquintal.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<pedido> listar() {
        return repository.findAll();
    }

    public Optional<pedido> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public pedido salvar(pedido pedido) {
        return repository.save(pedido);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
