package br.com.alexsander.reservas_de_salas.service;

import br.com.alexsander.reservas_de_salas.dto.CadastroReservaDto;
import br.com.alexsander.reservas_de_salas.dto.DadosListagemReservasDto;
import br.com.alexsander.reservas_de_salas.dto.DadosSalasComReservasDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.*;
import br.com.alexsander.reservas_de_salas.repository.ReservaRepository;
import br.com.alexsander.reservas_de_salas.repository.SalaRepository;
import br.com.alexsander.reservas_de_salas.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public void cadastrarReserva(CadastroReservaDto dto) {
        if (dto.dataHoraInicio().isAfter(dto.dataHoraFim()) || dto.dataHoraInicio().isEqual(dto.dataHoraFim()) ){
            throw new ValidacaoException("inicio não pode ser depois da data de finalização ou nao pode ser datas iguais!");
        }

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new ValidacaoException("Sala não encontrada!"));
        if (sala.getStatusSala() != StatusSala.ATIVA){
            throw new ValidacaoException("Não e possível reservar pois essa sala não esta ativa");
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));

       boolean conflitoDeHorario = reservaRepository.existsBySalaAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                sala,StatusReserva.CONFIRMADA,dto.dataHoraFim(),dto.dataHoraInicio());

       if (conflitoDeHorario){
           throw new ValidacaoException("Nao foi possivel fazer a reserva!");
       }
        reservaRepository.save(new Reserva(dto, usuario, sala));
    }

    public void cancelarReserva(Long reservaId, Long usuarioId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ValidacaoException("Reserva não encontrada!"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));

        boolean idIguais = reserva.getUsuario().getId().equals(usuarioId);
        if (!idIguais){
            throw new ValidacaoException("Somente o criador da reserva pode cancelar");
        }
        reserva.setStatus(StatusReserva.CANCELADA);

        reservaRepository.save(reserva);
    }


    public DadosSalasComReservasDto listarReservasDaSala(Long salaId) {
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new ValidacaoException("Sala nao encontrada"));

        List<DadosListagemReservasDto> reservas = sala.getReservas()
                .stream()
                .map(DadosListagemReservasDto::new)
                .toList();

        return new DadosSalasComReservasDto(sala.getNome(),sala.getTipoSala(), reservas);
    }
}
