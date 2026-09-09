package com.pontebella.mscitas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.mscitas.dto.CitaRequest;
import com.pontebella.mscitas.dto.CitaResponse;
import com.pontebella.mscitas.service.CitaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaResponse> crearCita(@RequestBody CitaRequest request) {
        CitaResponse response = citaService.crearCita(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CitaResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(citaService.listarPorCliente(clienteId));
    }

    @GetMapping("/estilista/{estilistaId}")
    public ResponseEntity<List<CitaResponse>> listarPorEstilista(@PathVariable Long estilistaId) {
        return ResponseEntity.ok(citaService.listarPorEstilista(estilistaId));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<CitaResponse>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.listarPorFecha(fecha));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<CitaResponse> confirmarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.confirmarCita(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<CitaResponse> cancelarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cancelarCita(id));
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<CitaResponse> completarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.finalizarcita(id));
    }
}
