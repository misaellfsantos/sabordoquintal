
package com.br.sabordoquintal.service;

import com.br.sabordoquintal.model.pedido;
import com.br.sabordoquintal.repository.RelatorioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioRepository repository;

    public RelatorioService(RelatorioRepository repository) {
        this.repository = repository;
    }

    // Faturamento total
    public BigDecimal faturamentoTotal() {
        return repository.calcularFaturamentoTotal();
    }

    // Quantidade de pedidos entregues
    public Long totalPedidosEntregues() {
        return repository.contarPedidosEntregues();
    }

    // Faturamento diário
    public BigDecimal faturamentoDiario() {
        LocalDate hoje = LocalDate.now();

        LocalDateTime inicio = hoje.atStartOfDay();
        LocalDateTime fim = hoje.plusDays(1).atStartOfDay();

        return repository.calcularFaturamentoPorPeriodo(
                inicio, fim
        );
    }

    // Faturamento mensal
    public BigDecimal faturamentoMensal() {
        LocalDate hoje = LocalDate.now();

        LocalDateTime inicio = hoje
                .withDayOfMonth(1)
                .atStartOfDay();

        LocalDateTime fim = hoje
                .plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();

        return repository.calcularFaturamentoPorPeriodo(
                inicio, fim
        );
    }

    // Pedidos entregues hoje
    public Long pedidosEntreguesHoje() {
        LocalDate hoje = LocalDate.now();

        return repository.contarPedidosPorPeriodo(
                hoje.atStartOfDay(),
                hoje.plusDays(1).atStartOfDay()
        );
    }

    // Pedidos entregues no mês
    public Long pedidosEntreguesMes() {
        LocalDate hoje = LocalDate.now();

        LocalDateTime inicio = hoje
                .withDayOfMonth(1)
                .atStartOfDay();

        LocalDateTime fim = hoje
                .plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();

        return repository.contarPedidosPorPeriodo(
                inicio, fim
        );
    }

    // Histórico de vendas
    public List<pedido> listarVendas() {
        return repository.listarVendasConcluidas();
    }
}
