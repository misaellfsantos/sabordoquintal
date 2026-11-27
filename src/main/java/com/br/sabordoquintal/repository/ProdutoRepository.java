
package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar produtos por categoria
    List<Produto> findByCategoriaIgnoreCase(String categoria);

    // Buscar pratos de determinado dia
    List<Produto> findByCategoriaIgnoreCaseAndDiaSemana(
            String categoria,
            Integer diaSemana
    );

    // Verificar se já existe um produto com esse nome
    boolean existsByNomeIgnoreCaseAndCategoriaIgnoreCase(
            String nome,
            String categoria
    );
}
