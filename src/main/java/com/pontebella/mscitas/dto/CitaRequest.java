package com.pontebella.mscitas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaRequest {
    
    private Long clienteId;
    private Long estilistaId;
    private Long servicioId;
    private LocalDate fecha;
    private LocalTime horaInicio;
}
