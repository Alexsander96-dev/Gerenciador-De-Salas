package br.com.alexsander.reservas_de_salas.service;

import br.com.alexsander.reservas_de_salas.dto.AtualizarUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.CadastroUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.TipoUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.UsuarioDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import br.com.alexsander.reservas_de_salas.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private Usuario usuario;

    @Test
    @DisplayName("Deveria retorna uma lista de usuário por tipo de usuário")
    void teste1(){
        //ARRANGE
        List<Usuario> usuarios = List.of(usuario);
        BDDMockito.given(repository.findByTipoUsuario(TipoUsuario.COMUM)).willReturn(usuarios);
        BDDMockito.given(usuario.getTipoUsuario()).willReturn(TipoUsuario.COMUM);

        //ACT
        List<TipoUsuarioDto> tipoUsuarioDtoList = service.listarUsuarioPorTipo(TipoUsuario.COMUM);

        //ASSERT
        boolean todosComum = tipoUsuarioDtoList.stream().allMatch(item -> item.tipoUsuario() == TipoUsuario.COMUM);
        Assertions.assertTrue(todosComum);
    }

    @Test
    @DisplayName("Não deveria deixar cadastrar usuário com telefone ou email ja cadastrado")
    void teste2(){
        //ARRANGE + ACT
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto(
                "Fulano da Silva", "11999999999", "fulano@email.com", TipoUsuario.COMUM);
        BDDMockito.given(repository.existsByTelefoneOrEmail(cadastroUsuarioDto.telefone(), cadastroUsuarioDto.email()))
                .willReturn(true);
        //ASSERT
        Assertions.assertThrows(ValidacaoException.class,() -> service.cadastrar(cadastroUsuarioDto));
    }

    @Test
    @DisplayName("deveria deixar cadastrar novo usuário")
    void teste3(){
        //ARRANGE
        CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto(
                "Fulano da Silva", "11999999999", "fulano@email.com", TipoUsuario.COMUM);
        BDDMockito.given(repository.existsByTelefoneOrEmail(cadastroUsuarioDto.telefone(), cadastroUsuarioDto.email()))
                .willReturn(false);

        //ACT
        Assertions.assertDoesNotThrow(() -> service.cadastrar(cadastroUsuarioDto));

        //ASSERT
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        BDDMockito.then(repository).should().save(captor.capture());

        Usuario usuarioSalvo = captor.getValue();
        Assertions.assertEquals(cadastroUsuarioDto.nome(), usuarioSalvo.getNome());
        Assertions.assertEquals(cadastroUsuarioDto.telefone(), usuarioSalvo.getTelefone());
        Assertions.assertEquals(cadastroUsuarioDto.email(), usuarioSalvo.getEmail());
        Assertions.assertEquals(cadastroUsuarioDto.tipoUsuario(), usuarioSalvo.getTipoUsuario());
    }

    @Test
    @DisplayName("Deveria deletar usuario com id")
    void teste4(){
        //ARRANGE
        Long id = 1L;
        Optional<Usuario> usuario1 = Optional.of(usuario);
        given(repository.findById(id)).willReturn(usuario1);

        //ACT
        service.deletar(id);

        //ASSERT
        then(repository).should().delete(usuario);

    }

    @Test
    @DisplayName("Deveria lançar exception quando nao encontra id para deletar usuário")
    void teste5(){
        //ARRANGE
        Long id = 1L;
        Optional<Usuario> usuarioNaoEncontrado = Optional.empty();
        given(repository.findById(id)).willReturn(usuarioNaoEncontrado);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> service.deletar(id));
    }

    @Test
    @DisplayName("Deveria lançar exception quando nao encontrar id para atualizar usuário")
    void teste6(){
        //ARRANGE
        AtualizarUsuarioDto atualizarUsuarioDto = new AtualizarUsuarioDto(4L,
                "Fulano da Silva", "11999999999", "fulano@email.com");
        Optional<Usuario> usuarioNaoEncontrado = Optional.empty();
        given(repository.findById(atualizarUsuarioDto.id())).willReturn(usuarioNaoEncontrado);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> service.atualizar(atualizarUsuarioDto));
    }

    @Test
    @DisplayName("Deveria atualizar usuário com id existente")
    void teste7(){
        //ARRANGE
        AtualizarUsuarioDto atualizarUsuarioDto = new AtualizarUsuarioDto(4L,
                "Fulano da Silva", "11999999999", "fulano@email.com");
        Optional<Usuario> usuarioEncontrado = Optional.of(usuario);
        given(repository.findById(atualizarUsuarioDto.id())).willReturn(usuarioEncontrado);

        //ACT
        service.atualizar(atualizarUsuarioDto);

        //ASSERT
        then(usuario).should().atualizarDados(atualizarUsuarioDto);
    }

    @Test
    @DisplayName("Deveria retornar usuário na busca pelo id")
    void teste8(){
        //ARRANGE
        Long id = 5L;
        Optional<Usuario> usuarioEncontrado = Optional.of(usuario);
        given(repository.findById(id)).willReturn(usuarioEncontrado);

        given(usuario.getId()).willReturn(id);
        given(usuario.getNome()).willReturn("Fulano da Silva");
        given(usuario.getTelefone()).willReturn("11999999999");
        given(usuario.getEmail()).willReturn("fulano@email.com");
        given(usuario.getTipoUsuario()).willReturn(TipoUsuario.COMUM);

        //ACT
        UsuarioDto usuarioDto = service.buscarPorId(id);

        //ASSERT
        Assertions.assertEquals(id, usuarioDto.id());
        Assertions.assertEquals("Fulano da Silva", usuarioDto.nome());
        Assertions.assertEquals("11999999999", usuarioDto.telefone());
        Assertions.assertEquals("fulano@email.com", usuarioDto.email());
        Assertions.assertEquals(TipoUsuario.COMUM, usuarioDto.tipoUsuario());
    }

    @Test
    @DisplayName("Deveria lançar exception quando buscar id inexistente")
    void teste9(){
        //ARRANGE
        Long id = 5L;
        Optional<Usuario> usuarioNaoEncontrado = Optional.empty();
        given(repository.findById(id)).willReturn(usuarioNaoEncontrado);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> service.buscarPorId(id));
    }
}

