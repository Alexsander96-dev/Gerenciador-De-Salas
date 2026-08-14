package br.com.alexsander.reservas_de_salas.model;

import br.com.alexsander.reservas_de_salas.dto.CadastroUsuarioDto;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;

    public Usuario(){}

    public Usuario(CadastroUsuarioDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.tipoUsuario = dto.tipoUsuario();
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
}
