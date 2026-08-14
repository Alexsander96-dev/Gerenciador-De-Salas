package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CadastroUsuarioDto(
        @NotBlank
        String nome,
        @NotBlank
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
        String telefone,
        @NotBlank
        @Email
        String email,
        @NotNull
        TipoUsuario tipoUsuario){

}
