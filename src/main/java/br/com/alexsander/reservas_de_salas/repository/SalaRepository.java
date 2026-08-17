package br.com.alexsander.reservas_de_salas.repository;

import br.com.alexsander.reservas_de_salas.dto.SalaDto;
import br.com.alexsander.reservas_de_salas.model.Sala;
import br.com.alexsander.reservas_de_salas.model.StatusSala;
import br.com.alexsander.reservas_de_salas.model.TipoSala;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    boolean existsByNomeIgnoreCaseAndUsuarioCriador(String nome, Usuario usuarioCriador);

    List<Sala> findByStatusSalaAndTipoSala(StatusSala statusSala, TipoSala tipoSala);

    List<Sala> findByStatusSala(StatusSala statusSala);
}
