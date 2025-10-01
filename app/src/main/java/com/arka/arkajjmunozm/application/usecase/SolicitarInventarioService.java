package com.arka.arkajjmunozm.application.usecase;

import com.arka.arkajjmunozm.domain.port.in.ISolicitarInventarioUseCase;
import com.arka.arkajjmunozm.domain.port.in.model.InventarioResult;
import com.arka.arkajjmunozm.domain.port.in.model.SolicitarInventarioCommand;
import com.arka.arkajjmunozm.domain.port.out.IInventarioServicePort;
import com.arka.arkajjmunozm.domain.port.out.dto.InventarioRequest;
import com.arka.arkajjmunozm.domain.port.out.dto.InventarioResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitarInventarioService implements ISolicitarInventarioUseCase {

    private final IInventarioServicePort inventarioServicePort;

    @Override
    public CompletableFuture<InventarioResult> solicitarInventario(SolicitarInventarioCommand command) {
        log.info("Iniciando proceso de solicitud de inventario para cliente:");

        InventarioRequest request = mapToRequest(command);
        return inventarioServicePort.solicitarInventario(request)
                .thenApply(this::mapToResult)
                .exceptionally(throwable -> {
                    log.error("Error solicitando inventario: {}", throwable.getMessage());
                    throw new RuntimeException("Error solicitando inventario", throwable);
                });
    }


    private InventarioRequest mapToRequest(SolicitarInventarioCommand command) {
        return new InventarioRequest(
                command.getProductId()
        );
    }

    private InventarioResult mapToResult(InventarioResponse response) {
        return InventarioResult.builder()
                .productoId(response.getProductoId())
                .cantidadDisponible(response.getCantidadDisponible())
                .build();
    }

}