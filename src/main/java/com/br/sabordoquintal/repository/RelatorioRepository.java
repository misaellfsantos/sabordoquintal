
package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<pedido, Long> {

    // Faturamento total de pedidos entregues
    @Query("""
        SELECT COALESCE(SUM(p.total), 0)
        FROM pedido p
        WHERE UPPER(p.status) = 'ENTREGUE'
    """)
    BigDecimal calcularFaturamentoTotal();

    // Quantidade de pedidos entregues
    @Query("""
        SELECT COUNT(p)
        FROM pedido p
        WHERE UPPER(p.status) = 'ENTREGUE'
    """)
    Long contarPedidosEntregues();

    // Faturamento por período
    @Query("""
        SELECT COALESCE(SUM(p.total), 0)
        FROM pedido p
        WHERE UPPER(p.status) = 'ENTREGUE'
        AND p.dataPedido BETWEEN :inicio AND :fim
    """)
    BigDecimal calcularFaturamentoPorPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    // Quantidade de pedidos entregues por período
    @Query("""
        SELECT COUNT(p)
        FROM pedido p
        WHERE UPPER(p.status) = 'ENTREGUE'
        AND p.dataPedido BETWEEN :inicio AND :fim
    """)
    Long contarPedidosPorPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );

    // Histórico de pedidos entregues
    @Query("""
        SELECT p
        FROM pedido p
        WHERE UPPER(p.status) = 'ENTREGUE'
        ORDER BY p.dataPedido DESC
    """)
    List<pedido> listarVendasConcluidas();
}
