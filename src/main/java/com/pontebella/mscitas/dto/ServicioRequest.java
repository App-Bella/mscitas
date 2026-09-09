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
public class ServicioRequest {
    
    private String nombre;
    private Integer duracionMinutos;
    private BigDecimal precio;
}
