package br.com.alexsander.reservas_de_salas.controller;

import br.com.alexsander.reservas_de_salas.dto.AtualizarUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.CadastroUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.TipoUsuarioDto;
import br.com.alexsander.reservas_de_salas.dto.UsuarioDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.TipoUsuario;

import br.com.alexsander.reservas_de_salas.service.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/tipo/{tipoUsuario}")
    public ResponseEntity<List<TipoUsuarioDto>> listarPorTipoUsuario(@PathVariable TipoUsuario tipoUsuario){
        try {
            List<TipoUsuarioDto> usuarios = usuarioService.listarUsuarioPorTipo(tipoUsuario);
            return ResponseEntity.ok(usuarios);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id){
        try {
            UsuarioDto dto = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(dto);
        }catch (ValidacaoException e){
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

    @PutMapping
    @Transactional
    public ResponseEntity<String> atualizar(@RequestBody @Valid AtualizarUsuarioDto dto){
        try {
            usuarioService.atualizar(dto);
            return ResponseEntity.ok().build();
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletar(@PathVariable Long id){
        try {
            usuarioService.deletar(id);
            return ResponseEntity.ok().build();
        }catch (ValidacaoException e ){
            return ResponseEntity.badRequest().build();
        }
    }
}
