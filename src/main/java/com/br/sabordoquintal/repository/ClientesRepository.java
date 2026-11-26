package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientesRepository extends JpaRepository<clientes, Long> {

    Optional<clientes> findByEmail(String email);

}