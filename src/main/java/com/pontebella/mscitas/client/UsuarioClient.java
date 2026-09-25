package com.pontebella.mscitas.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.pontebella.mscitas.dto.UsuarioValidacionResponse;
import com.pontebella.mscitas.exception.EstilistaNoValidoException;
import com.pontebella.mscitas.exception.RecursoNoEncontradoException;
import com.pontebella.mscitas.exception.ServicioNoDisponibleException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private static final String ROL_ESTILISTA = "ESTILISTA";

    private final RestClient usuariosRestClient;

    public void validarEstilista(Long estilistaId) {
        UsuarioValidacionResponse usuario = obtenerUsuario(estilistaId);

        if (!ROL_ESTILISTA.equalsIgnoreCase(usuario.getRol())) {
            throw new EstilistaNoValidoException(
                    "El usuario con id " + estilistaId + " no tiene rol ESTILISTA");
        }

        if (!usuario.isActivo()) {
            throw new EstilistaNoValidoException(
                    "El estilista con id " + estilistaId + " está inactivo");
        }
    }

    private UsuarioValidacionResponse obtenerUsuario(Long id) {
        try {
            return usuariosRestClient.get()
                    .uri("/usuarios/{id}/interno", id)
                    .retrieve()
                    .body(UsuarioValidacionResponse.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNoEncontradoException("No existe un usuario con id: " + id);
        } catch (ResourceAccessException ex) {
            throw new ServicioNoDisponibleException(
                    "No se pudo contactar a Ms-Usuarios para validar al estilista");
        }
    }
}