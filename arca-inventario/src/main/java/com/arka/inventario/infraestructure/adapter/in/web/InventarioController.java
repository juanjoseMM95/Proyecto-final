package com.arka.inventario.infraestructure.adapter.in.web;

import com.arka.inventario.application.service.InventarioService;
import com.arka.inventario.domain.model.InventarioResult;
import com.arka.inventario.domain.model.SolicitarInventarioCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService solicitarInventarioService;

    @GetMapping("/{productoId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'GESTOR', 'OPERADOR', 'USUARIO')")
    public CompletableFuture<ResponseEntity<InventarioResult>>
    solicitarInventario(@PathVariable("productoId") int productoId) {

        log.info("Recibida solicitud de inventario para el producto: {}", productoId);

        SolicitarInventarioCommand command = SolicitarInventarioCommand.builder()
                .productId(productoId)
                .build();

        return solicitarInventarioService.solicitarInventario(command)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    log.error("Error procesando solicitud de inventario: {}", throwable.getMessage());
                    return ResponseEntity.internalServerError().build();
                });
    }
}
