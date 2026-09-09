package com.pontebella.mscitas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontebella.mscitas.dto.ServicioRequest;
import com.pontebella.mscitas.dto.ServicioResponse;
import com.pontebella.mscitas.service.ServicioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/servicios")
@RequiredArgsConstructor
public class ServicioController {
    
    private final ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioResponse>> listarActivos() {
        return ResponseEntity.ok(servicioService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ServicioResponse> crearServicio(@RequestBody ServicioRequest request) {
        ServicioResponse response = servicioService.crearServicio(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponse> actualizarServicio(
            @PathVariable Long id, @RequestBody ServicioRequest request) {
        return ResponseEntity.ok(servicioService.actualizarServicio(id, request));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ServicioResponse> desactivarServicio(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.desactivarServicio(id));
    }
}
