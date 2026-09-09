package com.pontebella.mscitas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontebella.mscitas.dto.ServicioRequest;
import com.pontebella.mscitas.dto.ServicioResponse;
import com.pontebella.mscitas.entity.Servicio;
import com.pontebella.mscitas.exception.RecursoNoEncontradoException;
import com.pontebella.mscitas.repository.ServicioRepository;
import com.pontebella.mscitas.service.ServicioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    @Override
    public List<ServicioResponse> listarActivos() {
        return servicioRepository.findByActivoTrue()
                .stream()
                .map(this::aServicioResponse)
                .toList();
    }

    @Override
    public ServicioResponse obtenerPorId(Long id) {
        return aServicioResponse(obtenerEntidadPorId(id));
    }

    @Override
    public Servicio obtenerEntidadPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Servicio no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public ServicioResponse crearServicio(ServicioRequest request) {
        Servicio servicio = Servicio.builder()
                .nombre(request.getNombre())
                .duracionMinutos(request.getDuracionMinutos())
                .precio(request.getPrecio())
                .activo(true)
                .build();

        return aServicioResponse(servicioRepository.save(servicio));
    }

    @Override
    @Transactional
    public ServicioResponse actualizarServicio(Long id, ServicioRequest request) {
        Servicio servicio = obtenerEntidadPorId(id);

        servicio.setNombre(request.getNombre());
        servicio.setDuracionMinutos(request.getDuracionMinutos());
        servicio.setPrecio(request.getPrecio());

        return aServicioResponse(servicioRepository.save(servicio));
    }

    @Override
    @Transactional
    public ServicioResponse desactivarServicio(Long id) {
        Servicio servicio = obtenerEntidadPorId(id);
        servicio.setActivo(false);

        return aServicioResponse(servicioRepository.save(servicio));
    }

    private ServicioResponse aServicioResponse(Servicio servicio) {
        return ServicioResponse.builder()
                .id(servicio.getId())
                .nombre(servicio.getNombre())
                .duracionMinutos(servicio.getDuracionMinutos())
                .precio(servicio.getPrecio())
                .activo(servicio.getActivo())
                .build();
    }
}
