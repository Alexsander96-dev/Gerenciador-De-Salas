package br.com.alexsander.reservas_de_salas.model;

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
    private TipoSala tipo;

    @Enumerated(EnumType.STRING)
    private StatusSala statusSala;

    @OneToMany(mappedBy = "sala")
    private List<Reserva> reservas;

    public Sala(){}
}
