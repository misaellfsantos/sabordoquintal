
package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.itemPedido;
import com.br.sabordoquintal.model.pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository
        extends JpaRepository<itemPedido, Long> {

    List<itemPedido> findByPedido(pedido pedido);
}
