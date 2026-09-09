package com.pontebella.mscitas.service;

import java.time.LocalDate;
import java.util.List;

import com.pontebella.mscitas.dto.CitaRequest;
import com.pontebella.mscitas.dto.CitaResponse;

public interface CitaService {

    CitaResponse crearCita(CitaRequest request);

    CitaResponse obtenerPorId(Long id);

    List<CitaResponse> listarPorCliente(Long clienteId);

    List<CitaResponse> listarPorEstilista(Long estilistaId);

    List<CitaResponse> listarPorFecha(LocalDate fecha);

    CitaResponse confirmarCita(Long id);

    CitaResponse cancelarCita(Long id);

    CitaResponse finalizarcita(Long id);
}
