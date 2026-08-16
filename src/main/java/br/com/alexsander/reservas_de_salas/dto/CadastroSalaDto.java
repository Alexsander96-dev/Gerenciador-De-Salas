package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.TipoSala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroSalaDto(
        @NotNull Long usuarioId,
        @NotBlank String nome,
        @Positive int capacidade,
        @NotNull TipoSala tipoSala) {
}
