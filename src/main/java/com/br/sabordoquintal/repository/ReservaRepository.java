
package com.br.sabordoquintal.repository;

import com.br.sabordoquintal.model.reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<reserva, Long> {

    List<reserva> findByTelefone(String telefone);

}
