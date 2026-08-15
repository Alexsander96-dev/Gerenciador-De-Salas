package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;

public record TipoUsuarioDto(Long id, String nome, TipoUsuario tipoUsuario) {

    public TipoUsuarioDto(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getTipoUsuario());
    }
}
