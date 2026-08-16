package br.com.alexsander.reservas_de_salas.service;

import br.com.alexsander.reservas_de_salas.dto.CadastroSalaDto;
import br.com.alexsander.reservas_de_salas.dto.SalaDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.Sala;
import br.com.alexsander.reservas_de_salas.model.StatusSala;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import br.com.alexsander.reservas_de_salas.repository.SalaRepository;
import br.com.alexsander.reservas_de_salas.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SalaRepository salaRepository;

    public void cadastrarSala(CadastroSalaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));

        boolean nomeIdentico = salaRepository.existsByNomeIgnoreCaseAndUsuarioCriador(dto.nome(),usuario);
        if (nomeIdentico){
            throw new ValidacaoException("Não pode criar sala com nomes iguais!");
        }
        salaRepository.save(new Sala(dto, usuario));
    }

    public void deletarSala(Long idSala, Long idUsuario) {
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new ValidacaoException("Sala não encontrado!"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));

        boolean idIguais = sala.getUsuarioCriador().getId().equals(idUsuario);
        if (!idIguais){
            throw new ValidacaoException("Essa sala não pode ser deletada, pois você não e o criador da sala");
        }
        salaRepository.delete(sala);
    }

    public void atualizarSala(Long idSala, Long idUsuario, StatusSala statusSala) {
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new ValidacaoException("Sala não encontrada!"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));

        boolean idIguais = sala.getUsuarioCriador().getId().equals(idUsuario);
        if (!idIguais){
            throw new ValidacaoException("O status da sala não pode ser alterado, pois você não e o criador da sala");
        }else {
            sala.setStatusSala(statusSala);
            salaRepository.save(sala);
        }
    }

    public SalaDto buscarSalaPorId(Long idSala) {
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new ValidacaoException("Sala não encontrada!"));
        return new SalaDto(sala);
    }
}
