package br.com.alexsander.reservas_de_salas.controller;

import br.com.alexsander.reservas_de_salas.dto.CadastroSalaDto;
import br.com.alexsander.reservas_de_salas.dto.SalaDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.Sala;
import br.com.alexsander.reservas_de_salas.model.StatusSala;
import br.com.alexsander.reservas_de_salas.model.TipoSala;
import br.com.alexsander.reservas_de_salas.service.SalaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrarSala(@RequestBody @Valid CadastroSalaDto dto) {
        try {
            salaService.cadastrarSala(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Sala criada com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idSala}/usuario/{idUsuario}")
    public ResponseEntity<String> deletarSala(@PathVariable Long idSala, @PathVariable Long idUsuario){
        try {
            salaService.deletarSala(idSala,idUsuario);
            return ResponseEntity.accepted().body("Sala apagada com sucesso!");
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{idSala}/usuario/{idUsuario}")
    public ResponseEntity<String> atualizarSala(@PathVariable Long idSala, @PathVariable Long idUsuario, @RequestBody StatusSala statusSala){
        try {
            salaService.atualizarSala(idSala,idUsuario,statusSala);
            return ResponseEntity.ok().body("Status da sala alterado com sucesso");
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{idSala}")
    public ResponseEntity<SalaDto> buscarSalaPorId(@PathVariable Long idSala){
        try {
            SalaDto dto = salaService.buscarSalaPorId(idSala);
            return ResponseEntity.ok(dto);
        }catch (ValidacaoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SalaDto>> listarSalasAtivasPorTipo(@RequestParam TipoSala tipoSala){
        try {
            List<SalaDto> salas = salaService.listarSalasAtivasPorTipo(tipoSala);
            return ResponseEntity.ok(salas);
        }catch (ValidacaoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar/ativas")
    public ResponseEntity<List<SalaDto>> listarAtivas(){
        try {
            List<SalaDto> salasAtivas = salaService.listarSalasAtivas();
            return ResponseEntity.ok(salasAtivas);
        }catch (ValidacaoException e){
            return ResponseEntity.notFound().build();
        }
    }

}
