package br.com.alexsander.reservas_de_salas.controller;

import br.com.alexsander.reservas_de_salas.dto.CadastroUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.UsuarioDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.TipoUsuario;
import br.com.alexsander.reservas_de_salas.model.Usuario;
import br.com.alexsander.reservas_de_salas.repository.UsuarioRepository;
import br.com.alexsander.reservas_de_salas.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/tipo/{tipoUsuario}")
    public ResponseEntity<List<UsuarioDto>> listarPorTipoUsuario(@PathVariable TipoUsuario tipoUsuario){

        try {
            List<UsuarioDto> usuarios = usuarioService.listarUsuarioPorTipo(tipoUsuario);
            return ResponseEntity.ok(usuarios);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }


    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroUsuarioDto dto){
        try {
            usuarioService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
