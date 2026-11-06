
package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<produto, Long> {
}
