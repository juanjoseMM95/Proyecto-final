package com.arka.arkajjmunozm.infraestructure.adapter.in.web;

import com.arka.arkajjmunozm.domain.port.in.ISolicitarInventarioUseCase;
import com.arka.arkajjmunozm.domain.port.in.model.InventarioResult;
import com.arka.arkajjmunozm.domain.port.in.model.SolicitarInventarioCommand;
import com.arka.arkajjmunozm.domain.port.out.dto.InventarioRequest;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.CotizadorServiceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor

public class InventarioController {
    private final ISolicitarInventarioUseCase solicitarInventarioUseCase;

    @GetMapping("/{productoId}")
    public CompletableFuture<ResponseEntity<InventarioResult>>
    solicitarInventario(@PathVariable("productoId") int productoId) {

        log.info("Recibida solicitud de inventario para el producto: {}", productoId);

        // Ajusta según el builder de tu comando (puedes agregar más datos según lo requieras)
        SolicitarInventarioCommand command = SolicitarInventarioCommand.builder()
                .productId(productoId)
                .build();

        return solicitarInventarioUseCase.solicitarInventario(command)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    log.error("Error procesando solicitud de inventario: {}", throwable.getMessage());
                    return ResponseEntity.internalServerError().build();
                });
    }
}
