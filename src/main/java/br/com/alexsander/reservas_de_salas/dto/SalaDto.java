package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.Sala;
import br.com.alexsander.reservas_de_salas.model.StatusSala;
import br.com.alexsander.reservas_de_salas.model.TipoSala;

public record SalaDto(Long id, String nome, int capacidade, TipoSala tipoSala, StatusSala statusSala) {

    public SalaDto(Sala sala){
        this(sala.getId(), sala.getNome(), sala.getCapacidade(), sala.getTipoSala(),sala.getStatusSala());
    }
}
