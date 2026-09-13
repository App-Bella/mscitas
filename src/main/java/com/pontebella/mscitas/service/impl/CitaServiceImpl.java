package com.pontebella.mscitas.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontebella.mscitas.client.UsuarioClient;
import com.pontebella.mscitas.dto.CitaRequest;
import com.pontebella.mscitas.dto.CitaResponse;
import com.pontebella.mscitas.entity.Cita;
import com.pontebella.mscitas.entity.Servicio;
import com.pontebella.mscitas.enums.EstadoCita;
import com.pontebella.mscitas.exception.HorarioNoDisponibleException;
import com.pontebella.mscitas.exception.RecursoNoEncontradoException;
import com.pontebella.mscitas.exception.TransicionEstadoInvalidaException;
import com.pontebella.mscitas.repository.CitaRepository;
import com.pontebella.mscitas.service.CitaService;
import com.pontebella.mscitas.service.ServicioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final ServicioService servicioService;
    private final UsuarioClient usuarioClient;

    private static final List<EstadoCita> ESTADOS_ACTIVOS = List.of(EstadoCita.PENDIENTE, EstadoCita.CONFIRMADA);

    @Override
    @Transactional
    public CitaResponse crearCita(CitaRequest request) {
        usuarioClient.validarEstilista(request.getEstilistaId());

        Servicio servicio = servicioService.obtenerEntidadPorId(request.getServicioId());

        if (!Boolean.TRUE.equals(servicio.getActivo())) {
            throw new HorarioNoDisponibleException(
                    "El servicio '" + servicio.getNombre() + "' no está activo actualmente");
        }

        LocalTime horaFin = request.getHoraInicio().plusMinutes(servicio.getDuracionMinutos());

        validarDisponibilidadEstilista(request.getEstilistaId(), request.getFecha(),
                request.getHoraInicio(), horaFin);

        validarDisponibilidadCliente(request.getClienteId(), request.getFecha(),
                request.getHoraInicio(), horaFin);

        Cita cita = Cita.builder()
                .clienteId(request.getClienteId())
                .estilistaId(request.getEstilistaId())
                .servicio(servicio)
                .fecha(request.getFecha())
                .horaInicio(request.getHoraInicio())
                .horaFin(horaFin)
                .build();

        return aCitaResponse(citaRepository.save(cita));
    }

    @Override
    public CitaResponse obtenerPorId(Long id) {
        return aCitaResponse(buscarCitaPorId(id));
    }

    @Override
    public List<CitaResponse> listarPorCliente(Long clienteId) {
        return citaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::aCitaResponse)
                .toList();
    }

    @Override
    public List<CitaResponse> listarPorEstilista(Long estilistaId) {
        return citaRepository.findByEstilistaId(estilistaId)
                .stream()
                .map(this::aCitaResponse)
                .toList();
    }

    @Override
    public List<CitaResponse> listarPorFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha)
                .stream()
                .map(this::aCitaResponse)
                .toList();
    }

    @Override
    @Transactional
    public CitaResponse confirmarCita(Long id) {
        Cita cita = buscarCitaPorId(id);

        if (cita.getEstado() != EstadoCita.PENDIENTE) {
            throw new TransicionEstadoInvalidaException(
                    "Solo una cita PENDIENTE puede confirmarse. Estado actual: " + cita.getEstado());
        }

        cita.setEstado(EstadoCita.CONFIRMADA);
        return aCitaResponse(citaRepository.save(cita));
    }

    @Override
    @Transactional
    public CitaResponse cancelarCita(Long id) {
        Cita cita = buscarCitaPorId(id);

        if (cita.getEstado() == EstadoCita.FINALIZADA || cita.getEstado() == EstadoCita.CANCELADA) {
            throw new TransicionEstadoInvalidaException(
                    "No se puede cancelar una cita en estado: " + cita.getEstado());
        }

        cita.setEstado(EstadoCita.CANCELADA);
        return aCitaResponse(citaRepository.save(cita));
    }

    @Override
    @Transactional
    public CitaResponse finalizarcita(Long id) {
        Cita cita = buscarCitaPorId(id);

        if (cita.getEstado() != EstadoCita.CONFIRMADA) {
            throw new TransicionEstadoInvalidaException(
                    "Solo una cita CONFIRMADA puede completarse. Estado actual: " + cita.getEstado());
        }

        cita.setEstado(EstadoCita.FINALIZADA);
        return aCitaResponse(citaRepository.save(cita));
    }

    private Cita buscarCitaPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Cita no encontrada con id: " + id));
    }

    private void validarDisponibilidadEstilista(Long estilistaId, LocalDate fecha,
                                             LocalTime horaInicio, LocalTime horaFin) {
    List<Cita> cruces = citaRepository.buscarCrucesEstilista(
            estilistaId, fecha, horaInicio, horaFin, ESTADOS_ACTIVOS);

    if (!cruces.isEmpty()) {
        throw new HorarioNoDisponibleException(
                "El estilista ya tiene una cita entre " + horaInicio + " y " + horaFin
                        + " el " + fecha);
    }
}

private void validarDisponibilidadCliente(Long clienteId, LocalDate fecha,
                                           LocalTime horaInicio, LocalTime horaFin) {
    List<Cita> cruces = citaRepository.buscarCrucesCliente(
            clienteId, fecha, horaInicio, horaFin, ESTADOS_ACTIVOS);

    if (!cruces.isEmpty()) {
        throw new HorarioNoDisponibleException(
                "Ya tienes una cita agendada entre " + horaInicio + " y " + horaFin
                        + " el " + fecha);
    }
}

    private CitaResponse aCitaResponse(Cita cita) {
        return CitaResponse.builder()
                .id(cita.getId())
                .clienteId(cita.getClienteId())
                .estilistaId(cita.getEstilistaId())
                .servicioId(cita.getServicio().getId())
                .servicioNombre(cita.getServicio().getNombre())
                .fecha(cita.getFecha())
                .horaInicio(cita.getHoraInicio())
                .horaFin(cita.getHoraFin())
                .estado(cita.getEstado())
                .build();
    }
}
