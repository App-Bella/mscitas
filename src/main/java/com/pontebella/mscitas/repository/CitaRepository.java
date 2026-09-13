package com.pontebella.mscitas.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pontebella.mscitas.entity.Cita;
import com.pontebella.mscitas.enums.EstadoCita;

public interface CitaRepository extends JpaRepository <Cita, Long>{
    
    List<Cita> findByClienteId(Long clienteId);

    List<Cita> findByEstilistaId(Long estilistaId);

    List<Cita> findByFecha(LocalDate fecha);

    List<Cita> findByEstilistaIdAndFecha(Long estilistaId, LocalDate fecha);

    @Query("""
            SELECT c FROM Cita c
            WHERE c.estilistaId = :estilistaId
              AND c.fecha = :fecha
              AND c.estado IN :estadosActivos
              AND c.horaInicio < :horaFin
              AND c.horaFin > :horaInicio
            """)
    List<Cita> buscarCrucesEstilista(
            @Param("estilistaId") Long estilistaId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("estadosActivos") List<EstadoCita> estadosActivos
    );

    @Query("""
            SELECT c FROM Cita c
            WHERE c.clienteId = :clienteId
              AND c.fecha = :fecha
              AND c.estado IN :estadosActivos
              AND c.horaInicio < :horaFin
              AND c.horaFin > :horaInicio
            """)
    List<Cita> buscarCrucesCliente(
            @Param("clienteId") Long clienteId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("estadosActivos") List<EstadoCita> estadosActivos
    );
}
