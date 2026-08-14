package br.com.alexsander.reservas_de_salas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AtualizarUsuarioDto(
        @NotNull
        Long id,
        String nome,
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,
        String email) {
}
