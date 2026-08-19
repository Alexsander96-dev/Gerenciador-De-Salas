package br.com.alexsander.reservas_de_salas.controller;

import br.com.alexsander.reservas_de_salas.dto.CadastroReservaDto;
import br.com.alexsander.reservas_de_salas.dto.DadosSalasComReservasDto;
import br.com.alexsander.reservas_de_salas.exception.ValidacaoException;
import br.com.alexsander.reservas_de_salas.model.StatusReserva;
import br.com.alexsander.reservas_de_salas.service.ReservaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrarReserva(@RequestBody @Valid CadastroReservaDto dto){
        try {
            service.cadastrarReserva(dto);
            return ResponseEntity.ok().body("Reserva cadastrada com sucesso!");
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{reservaId}/usuario/{usuarioId}")
    public ResponseEntity<String> cancelarReserva(@PathVariable Long reservaId, @PathVariable Long usuarioId){
        try {
            service.cancelarReserva(reservaId,usuarioId);
            return ResponseEntity.ok().body("Reserva cancelada com sucesso!");
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{salaId}")
    public ResponseEntity<DadosSalasComReservasDto> listarReservasDaSala(@PathVariable Long salaId){
        try {
            DadosSalasComReservasDto dto = service.listarReservasDaSala(salaId);
            return ResponseEntity.ok(dto);
        }catch (ValidacaoException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
