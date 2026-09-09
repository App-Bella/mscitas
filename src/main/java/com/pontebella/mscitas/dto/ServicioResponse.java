package com.pontebella.mscitas.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioResponse {
    
    private Long id;
    private String nombre;
    private Integer duracionMinutos;
    private BigDecimal precio;
    private Boolean activo;
}
