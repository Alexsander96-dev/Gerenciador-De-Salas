package br.com.alexsander.reservas_de_salas.repository;

import br.com.alexsander.reservas_de_salas.model.Reserva;
import br.com.alexsander.reservas_de_salas.model.Sala;
import br.com.alexsander.reservas_de_salas.model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsBySalaAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
            Sala sala,
            StatusReserva status,
            LocalDateTime fimDesejado,
            LocalDateTime inicioDesejado
    );
}
