package br.com.alexsander.reservas_de_salas.repository;

import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

    boolean existsByNomeOrTelefoneOrEmail(String nome,String telefone,String email);

    Optional<Usuario> findByNome(String idOuNome);
}
