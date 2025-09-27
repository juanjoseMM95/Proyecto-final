package com.arka.arkajjmunozm.domain.port.out;
import com.arka.arkajjmunozm.infraestructure.adapter.out.microservice.dto.CotizacionRequestDto;
import com.arka.arkajjmunozm.infraestructure.adapter.out.microservice.dto.CotizacionResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CotizadorServicePort {
    /**
     * Crea una nueva cotización
     */
    CompletableFuture<CotizacionResponseDto> crearCotizacion(CotizacionRequestDto request);

    /**
     * Obtiene una cotización por ID
     */
    CompletableFuture<CotizacionResponseDto> obtenerCotizacion(String cotizacionId);

    /**
     * Obtiene todas las cotizaciones de un cliente
     */
    CompletableFuture<List<CotizacionResponseDto>> obtenerCotizacionesPorCliente(Long customerId);

    /**
     * Actualiza el estado de una cotización
     */
    CompletableFuture<CotizacionResponseDto> actualizarEstadoCotizacion(String cotizacionId, String nuevoEstado);

    /**
     * Verifica si el servicio está disponible
     */
    CompletableFuture<Boolean> isServiceAvailable();
}
