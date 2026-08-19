package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.Reserva;
import br.com.alexsander.reservas_de_salas.model.StatusReserva;

import java.time.LocalDateTime;

public record DadosListagemReservasDto(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, StatusReserva status) {

    public DadosListagemReservasDto(Reserva reserva){
        this(reserva.getDataHoraInicio(), reserva.getDataHoraFim(), reserva.getStatus());
    }
}
