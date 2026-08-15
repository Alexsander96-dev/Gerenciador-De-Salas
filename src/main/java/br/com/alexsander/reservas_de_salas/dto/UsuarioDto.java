package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;

public record UsuarioDto(Long id,String nome, String telefone, String email,TipoUsuario tipoUsuario) {

    public UsuarioDto(Usuario usuario){
        this(usuario.getId(),usuario.getNome(), usuario.getTelefone(), usuario.getEmail(), usuario.getTipoUsuario());
    }
}
