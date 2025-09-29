package com.arka.arkajjmunozm.application.usecase;

import com.arka.arkajjmunozm.domain.exception.CotizacionException;
import com.arka.arkajjmunozm.domain.port.in.ISolicitarCotizacionUseCase;
import com.arka.arkajjmunozm.domain.port.in.model.CotizacionResult;
import com.arka.arkajjmunozm.domain.port.in.model.SolicitarCotizacionCommand;
import com.arka.arkajjmunozm.domain.port.out.ICotizadorServicePort;
import com.arka.arkajjmunozm.domain.port.out.dto.CotizacionRequest;
import com.arka.arkajjmunozm.domain.port.out.dto.ProductoCotizacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SolicitarCotizacionService implements ISolicitarCotizacionUseCase
{
    private final ICotizadorServicePort cotizadorServicePort;

    @Override
    public CompletableFuture<CotizacionResult> solicitarCotizacion(SolicitarCotizacionCommand command) {
        log.info("Iniciando solicitud de cotización para cliente: {}", command.getCustomerId());

        // Convertir Command a Request
        CotizacionRequest request = mapToRequest(command);
        // Llamar al puerto de salida y mapear la respuesta
        return cotizadorServicePort.solicitarCotizacion(request)
                .thenApply(response -> CotizacionResult.builder()
                        .cotizacionId(response.getCotizacionId())
                        .customerId(response.getCustomerId())
                        .total(response.getTotal())
                        .estado(response.getEstado())
                        .productos(response.getProductos().stream()
                                .map(p -> CotizacionResult.ProductoCotizado.builder()
                                        .productId(p.getProductId())
                                        .cantidad(p.getCantidad())
                                        .precioUnitario(p.getPrecioUnitario())
                                        .subtotal(p.getSubtotal())
                                        .descuentoAplicado(p.getDescuentoAplicado())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .exceptionally(throwable -> {
                    log.error("Error al procesar la cotización: {}", throwable.getMessage());
                    throw new CotizacionException("Error al procesar la cotización", throwable);
                });
    }

    private CotizacionRequest mapToRequest(SolicitarCotizacionCommand command) {
        return new CotizacionRequest(
                command.getCustomerId(),
                command.getProductos().stream()
                        .map(p -> new ProductoCotizacion(p.getProductId(), p.getCantidad()))
                        .collect(Collectors.toList())
        );
    }
}
