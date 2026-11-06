
package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<conta, Long> {

    Optional<conta> findByEmail(String email);
}
