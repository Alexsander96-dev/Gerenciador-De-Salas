package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;

public record UsuarioDto(Long id, TipoUsuario tipoUsuario) {

    public UsuarioDto(Usuario usuario){
        this(usuario.getId(),usuario.getTipoUsuario());
    }
}
