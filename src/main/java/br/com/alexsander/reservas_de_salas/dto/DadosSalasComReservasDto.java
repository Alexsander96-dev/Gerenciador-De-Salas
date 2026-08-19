package br.com.alexsander.reservas_de_salas.dto;

import br.com.alexsander.reservas_de_salas.model.TipoSala;

import java.util.List;

public record DadosSalasComReservasDto(String nome, TipoSala tipoSala, List<DadosListagemReservasDto> dto) {
}
