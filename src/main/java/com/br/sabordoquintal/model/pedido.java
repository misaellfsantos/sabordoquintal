
package com.br.sabordoquintal.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private clientes cliente;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private String status;

    private LocalDateTime dataPedido;

    public pedido() {
    }

    public pedido(clientes cliente, BigDecimal total, String status) {
        this.cliente = cliente;
        this.total = total;
        this.status = status;
        this.dataPedido = LocalDateTime.now();
    }

    @PrePersist
    public void antesDeSalvar() {
        if (dataPedido == null) {
            dataPedido = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public clientes getCliente() {
        return cliente;
    }

    public void setCliente(clientes cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }
}
