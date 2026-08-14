package br.com.alexsander.reservas_de_salas.repository;

import br.com.alexsander.reservas_de_salas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
