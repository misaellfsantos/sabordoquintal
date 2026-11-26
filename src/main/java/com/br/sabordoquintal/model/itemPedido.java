package com.br.sabordoquintal.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_pedido")

public class itemPedido {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "pedido_id", nullable = false)
        private pedido pedido;

        @ManyToOne
        @JoinColumn(name = "produto_id", nullable = false)
        private produto produto;

        @Column(nullable = false)
        private Integer quantidade;

        @Column(nullable = false)
        private BigDecimal precoUnitario;

        public itemPedido() {
        }

        public itemPedido(pedido pedido, produto produto,
                          Integer quantidade, BigDecimal precoUnitario) {
            this.pedido = pedido;
            this.produto = produto;
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public pedido getPedido() {
            return pedido;
        }

        public void setPedido(pedido pedido) {
            this.pedido = pedido;
        }

        public produto getProduto() {
            return produto;
        }

        public void setProduto(produto produto) {
            this.produto = produto;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }

        public BigDecimal getPrecoUnitario() {
            return precoUnitario;
        }

        public void setPrecoUnitario(BigDecimal precoUnitario) {
            this.precoUnitario = precoUnitario;
        }

        public BigDecimal getSubtotal() {
            return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

}