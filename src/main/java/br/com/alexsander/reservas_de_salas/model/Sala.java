package br.com.alexsander.reservas_de_salas.model;

import br.com.alexsander.reservas_de_salas.dto.CadastroSalaDto;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int capacidade;

    @Enumerated(EnumType.STRING)
    private TipoSala tipoSala;

    @Enumerated(EnumType.STRING)
    private StatusSala statusSala;

    @OneToMany(mappedBy = "sala")
    private List<Reserva> reservas;

    //Relacionamento criado para vincular o usuário que criou a sala
    @ManyToOne
    @JoinColumn(name = "usuario_criador_id")
    private Usuario usuarioCriador;

    public Sala(){}

    public Sala(CadastroSalaDto dto, Usuario usuario) {
        this.usuarioCriador = usuario;
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
        this.tipoSala = dto.tipoSala();
        this.statusSala = StatusSala.ATIVA;
    }

    public void setStatusSala(StatusSala statusSala) {
        this.statusSala = statusSala;
    }

    public Usuario getUsuarioCriador() {
        return usuarioCriador;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public TipoSala getTipoSala() {
        return tipoSala;
    }

    public StatusSala getStatusSala() {
        return statusSala;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}
