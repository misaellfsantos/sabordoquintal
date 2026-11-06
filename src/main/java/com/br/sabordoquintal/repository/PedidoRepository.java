
package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<pedido, Long> {
}
