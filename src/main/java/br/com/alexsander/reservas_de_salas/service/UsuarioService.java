package br.com.alexsander.reservas_de_salas.service;

import br.com.alexsander.reservas_de_salas.dto.AtualizarUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.CadastroUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.TipoUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.UsuarioDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import br.com.alexsander.reservas_de_salas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<TipoUsuarioDto> listarUsuarioPorTipo(TipoUsuario tipoUsuario) {
       return usuarioRepository.findByTipoUsuario(tipoUsuario)
                .stream()
                .map(TipoUsuarioDto::new)
                .toList();
    }

    public void cadastrar(CadastroUsuarioDto dto) {
        boolean jaCadastrado = usuarioRepository.existsByTelefoneOrEmail(dto.telefone(),dto.email());

        if (jaCadastrado){
            throw new ValidacaoException("Dados já cadastrado!");
        }
        usuarioRepository.save(new Usuario(dto));
    }

    public void atualizar(AtualizarUsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.id())
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));
        usuario.atualizarDados(dto);
    }

    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
    }

    public UsuarioDto buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado!"));
        return new UsuarioDto(usuario);
    }
}
