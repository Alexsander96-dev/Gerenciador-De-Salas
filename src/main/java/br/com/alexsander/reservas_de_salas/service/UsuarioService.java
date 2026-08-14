package br.com.alexsander.reservas_de_salas.service;

import br.com.alexsander.reservas_de_salas.dto.AtualizarUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.CadastroUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.UsuarioDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import br.com.alexsander.reservas_de_salas.repository.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDto> listarUsuarioPorTipo(TipoUsuario tipoUsuario) {
       return usuarioRepository.findByTipoUsuario(tipoUsuario)
                .stream()
                .map(UsuarioDto::new)
                .toList();
    }

    public void cadastrar(CadastroUsuarioDto dto) {
        boolean jaCadastrado = usuarioRepository.existsByNomeOrTelefoneOrEmail(dto.nome(),dto.telefone(),dto.email());

        if (jaCadastrado){
            throw new ValidacaoException("Dados já cadastrado!");
        }
        usuarioRepository.save(new Usuario(dto));
    }

    public void atualizar(AtualizarUsuarioDto dto) {
        Usuario usuario = usuarioRepository.getReferenceById(dto.id());
        usuario.atualizarDados(dto);
    }

    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
    }

}
