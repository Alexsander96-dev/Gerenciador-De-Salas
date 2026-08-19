package br.com.alexsander.reservas_de_salas.model;

import br.com.alexsander.reservas_de_salas.dto.CadastroReservaDto;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraInicio;

    private LocalDateTime dataHoraFim;

    @Enumerated(value = EnumType.STRING)
    private StatusReserva status;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Reserva(){}

    public Reserva(CadastroReservaDto dto, Usuario usuario, Sala sala) {
        this.dataHoraInicio = dto.dataHoraInicio();
        this.dataHoraFim = dto.dataHoraFim();
        this.status = StatusReserva.CONFIRMADA;
        this.usuario = usuario;
        this.sala = sala;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public Sala getSala() {
        return sala;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }
}
