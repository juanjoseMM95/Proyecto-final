package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence;

import com.arka.arkajjmunozm.domain.port.out.ICotizadorServicePort;
import com.arka.arkajjmunozm.domain.port.out.dto.CotizacionRequest;
import com.arka.arkajjmunozm.domain.port.out.dto.CotizacionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class CotizadorServiceAdapter implements ICotizadorServicePort
{
    private final WebClient cotizadorWebClient;

    @Override
    public CompletableFuture<CotizacionResponse> solicitarCotizacion(CotizacionRequest request) {
        log.debug("Solicitando cotización para cliente: {}", request.getCustomerId());
        return cotizadorWebClient.post()
                .uri("/api/cotizaciones")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CotizacionResponse.class)
                .doOnSuccess(response -> log.debug("Cotización generada con ID: {}", response.getCotizacionId()))
                .doOnError(error -> log.error("Error al solicitar cotización: {}", error.getMessage()))
                .toFuture();
    }

    @Override
    public CompletableFuture<CotizacionResponse> obtenerCotizacion(String cotizacionId) {
        log.debug("Consultando cotización con ID: {}", cotizacionId);
        return cotizadorWebClient.get()
                .uri("/api/cotizaciones/{id}", cotizacionId)
                .retrieve()
                .bodyToMono(CotizacionResponse.class)
                .doOnSuccess(response -> log.debug("Cotización recuperada: {}", response))
                .doOnError(error -> log.error("Error al obtener cotización {}: {}", cotizacionId, error.getMessage()))
                .toFuture();
    }

    @Override
    public CompletableFuture<Boolean> isServiceAvailable() {
        return cotizadorWebClient.get()
                .uri("/api/cotizaciones/health")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> true)
                .onErrorReturn(false)
                .toFuture();
    }
}
