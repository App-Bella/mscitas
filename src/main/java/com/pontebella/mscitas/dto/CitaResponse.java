package com.pontebella.mscitas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.pontebella.mscitas.enums.EstadoCita;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaResponse {
    
    private Long id;
    private Long clienteId;
    private Long estilistaId;
    private Long servicioId;
    private String servicioNombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EstadoCita estado;
}
