package com.arka.arkajjmunozm.infraestructure.adapter.in.web;

import com.arka.arkajjmunozm.domain.port.in.ISolicitarCotizacionUseCase;
import com.arka.arkajjmunozm.domain.port.in.model.CotizacionResult;
import com.arka.arkajjmunozm.domain.port.in.model.SolicitarCotizacionCommand;
import com.arka.arkajjmunozm.domain.port.out.dto.CotizacionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/cotizaciones")
@RequiredArgsConstructor
public class CotizacionController {

    private final ISolicitarCotizacionUseCase solicitarCotizacionUseCase;
    private final com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.CotizadorServiceAdapter cotizadorServiceAdapter;

    @PostMapping
    public CompletableFuture<ResponseEntity<CotizacionResult>> solicitarCotizacion(
            @RequestBody CotizacionRequest request) {
        log.info("Recibida solicitud de cotización para cliente: {}", request.getCustomerId());

        SolicitarCotizacionCommand command = mapToCommand(request);

        return solicitarCotizacionUseCase.solicitarCotizacion(command)
                .thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    log.error("Error procesando solicitud de cotización: {}", throwable.getMessage());
                    return ResponseEntity.internalServerError().build();
                });
    }

    private SolicitarCotizacionCommand mapToCommand(CotizacionRequest request) {
        return SolicitarCotizacionCommand.builder()
                .customerId(request.getCustomerId())
                .productos(request.getProductos().stream()
                        .map(p -> SolicitarCotizacionCommand.ProductoSolicitud.builder()
                                .productId(p.getProductId())
                                .cantidad(p.getCantidad())
                                .build())
                        .toList())
                .build();
    }
    @GetMapping("/check-cotizador")
    public CompletableFuture<ResponseEntity<String>> checkCotizadorService() {
        return cotizadorServiceAdapter.isServiceAvailable()
                .thenApply(available -> available
                        ? ResponseEntity.ok("Servicio de cotización disponible")
                        : ResponseEntity.status(503).body("Servicio de cotización no disponible"));
    }

}

