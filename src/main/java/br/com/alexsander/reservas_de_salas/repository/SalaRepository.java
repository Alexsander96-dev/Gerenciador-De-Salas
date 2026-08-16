package br.com.alexsander.reservas_de_salas.repository;

import br.com.alexsander.reservas_de_salas.model.Sala;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNomeIgnoreCaseAndUsuarioCriador(String nome, Usuario usuarioCriador);
}
