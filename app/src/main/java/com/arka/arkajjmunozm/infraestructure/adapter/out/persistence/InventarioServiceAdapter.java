package com.arka.arkajjmunozm.infraestructure.adapter.out.persistence;

import com.arka.arkajjmunozm.domain.port.out.IInventarioServicePort;
import com.arka.arkajjmunozm.domain.port.out.dto.InventarioRequest;
import com.arka.arkajjmunozm.domain.port.out.dto.InventarioResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor

public class InventarioServiceAdapter implements IInventarioServicePort {
    @Qualifier("inventarioWebClient")
    private final WebClient inventarioWebClient;
    @Override
    public CompletableFuture<InventarioResponse> solicitarInventario(InventarioRequest request) {
        log.debug("Consultando inventario para producto: {}", request.getProductoId());
        return inventarioWebClient.get()
                .uri("/api/inventario/{id}", request.getProductoId())
                .retrieve()
                .bodyToMono(InventarioResponse.class)
                .doOnSuccess(response -> log.debug("Inventario recuperado: {}", response))
                .doOnError(error -> log.error("Error al obtener inventario para producto {}: {}", request.getProductoId(), error.getMessage()))
                .toFuture();
    }
}
