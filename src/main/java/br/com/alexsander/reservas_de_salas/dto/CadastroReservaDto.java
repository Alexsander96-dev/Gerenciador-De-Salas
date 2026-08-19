package br.com.alexsander.reservas_de_salas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CadastroReservaDto(
        @NotNull
        Long usuarioId,
        @NotNull
        Long salaId,
        @NotNull
        LocalDateTime dataHoraInicio,
        @NotNull
        LocalDateTime dataHoraFim) {
}
