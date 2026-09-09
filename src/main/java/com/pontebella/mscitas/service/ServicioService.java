package com.pontebella.mscitas.service;

import java.util.List;

import com.pontebella.mscitas.dto.ServicioRequest;
import com.pontebella.mscitas.dto.ServicioResponse;
import com.pontebella.mscitas.entity.Servicio;

public interface ServicioService {

    List<ServicioResponse> listarActivos();

    ServicioResponse obtenerPorId(Long id);

    Servicio obtenerEntidadPorId(Long id);

    ServicioResponse crearServicio(ServicioRequest request);

    ServicioResponse actualizarServicio(Long id, ServicioRequest request);

    ServicioResponse desactivarServicio(Long id);
}
