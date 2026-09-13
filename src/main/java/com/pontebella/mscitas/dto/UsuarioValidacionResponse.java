package com.pontebella.mscitas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioValidacionResponse {
    
    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private String telefono;
    private boolean activo;
}
