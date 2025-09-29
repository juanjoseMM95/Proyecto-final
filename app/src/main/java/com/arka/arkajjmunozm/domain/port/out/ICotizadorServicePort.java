package com.arka.arkajjmunozm.domain.port.out;
import com.arka.arkajjmunozm.domain.port.out.dto.CotizacionRequest;
import com.arka.arkajjmunozm.domain.port.out.dto.CotizacionResponse;
import java.util.concurrent.CompletableFuture;

public interface ICotizadorServicePort {
    /**
     * Envía una solicitud de cotización al microservicio
     * @param request Datos de la solicitud de cotización
     * @return CompletableFuture con la respuesta de la cotización
     */
    CompletableFuture<CotizacionResponse> solicitarCotizacion(CotizacionRequest request);

    /**
     * Obtiene el estado de una cotización existente
     * @param cotizacionId Identificador único de la cotización
     * @return CompletableFuture con los datos actualizados de la cotización
     */
    CompletableFuture<CotizacionResponse> obtenerCotizacion(String cotizacionId);

    /**
     * Verifica si el servicio de cotizaciones está disponible
     * @return true si el servicio está disponible
     */
    CompletableFuture<Boolean> isServiceAvailable();
}

