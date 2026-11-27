
package com.br.sabordoquintal.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProdutoInicializador implements CommandLineRunner {

    private final ProdutoService produtoService;

    public ProdutoInicializador(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public void run(String... args) {

        // DOMINGO - 0
        produtoService.cadastrarProdutoInicial(
                "Costela Bovina com Batatas Rústicas",
                new BigDecimal("79.90"), "PRATO", 0
        );

        // SEGUNDA-FEIRA - 1
        produtoService.cadastrarProdutoInicial(
                "Feijoada Artesanal",
                new BigDecimal("68.90"), "PRATO", 1
        );

        produtoService.cadastrarProdutoInicial(
                "Frango do Chef",
                new BigDecimal("59.90"), "PRATO", 1
        );

        // TERÇA-FEIRA - 2
        produtoService.cadastrarProdutoInicial(
                "Filé ao Molho",
                new BigDecimal("74.90"), "PRATO", 2
        );

        produtoService.cadastrarProdutoInicial(
                "Cordeiro ao Vinho Tinto",
                new BigDecimal("118.00"), "PRATO", 2
        );

        // QUARTA-FEIRA - 3
        produtoService.cadastrarProdutoInicial(
                "Frango Recheado",
                new BigDecimal("82.00"), "PRATO", 3
        );

        produtoService.cadastrarProdutoInicial(
                "Lombo de Ervas e Limão",
                new BigDecimal("98.00"), "PRATO", 3
        );

        // QUINTA-FEIRA - 4
        produtoService.cadastrarProdutoInicial(
                "Ancho ao Molho",
                new BigDecimal("134.20"), "PRATO", 4
        );

        produtoService.cadastrarProdutoInicial(
                "Nhoque de Costela",
                new BigDecimal("89.00"), "PRATO", 4
        );

        // SEXTA-FEIRA - 5
        produtoService.cadastrarProdutoInicial(
                "Lasanha à Bolonhesa",
                new BigDecimal("75.00"), "PRATO", 5
        );

        produtoService.cadastrarProdutoInicial(
                "Filé Mignon ao Molho",
                new BigDecimal("112.00"), "PRATO", 5
        );

        // SÁBADO - 6
        produtoService.cadastrarProdutoInicial(
                "Risoto de Costela",
                new BigDecimal("80.00"), "PRATO", 6
        );

        produtoService.cadastrarProdutoInicial(
                "Cordeiro",
                new BigDecimal("75.00"), "PRATO", 6
        );

        // BEBIDAS
        produtoService.cadastrarProdutoInicial(
                "Coca-Cola",
                new BigDecimal("7.90"), "BEBIDA", null
        );

        produtoService.cadastrarProdutoInicial(
                "Suco natural",
                new BigDecimal("5.00"), "BEBIDA", null
        );

        produtoService.cadastrarProdutoInicial(
                "Água",
                new BigDecimal("4.00"), "BEBIDA", null
        );

        System.out.println("Produtos iniciais verificados!");
    }
}
