

package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<conta, Long> {

    // Buscar uma conta pelo email durante o login
    Optional<conta> findByEmail(String email);

    // Verificar se o email já está cadastrado
    boolean existsByEmail(String email);

}
