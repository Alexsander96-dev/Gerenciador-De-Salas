package br.com.alexsander.reservas_de_salas.model;

import br.com.alexsander.reservas_de_salas.dto.AtualizarUsuarioDto;
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


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void atualizarDados(AtualizarUsuarioDto dto) {
        if (dto.nome() != null){
            this.nome = dto.nome();
        }
        if (dto.email() != null){
            this.email = dto.email();
        }
        if (dto.telefone() != null){
            this.telefone = dto.telefone();
        }
    }


}
