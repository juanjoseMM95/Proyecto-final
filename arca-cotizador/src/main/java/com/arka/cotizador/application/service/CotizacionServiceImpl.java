package com.arka.cotizador.application.service;

import com.arka.cotizador.domain.model.CotizacionRequest;
import com.arka.cotizador.domain.model.CotizacionResponse;
import com.arka.cotizador.domain.model.ItemCotizado;
import com.arka.cotizador.domain.model.ProductoCotizado;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Service
public class CotizacionServiceImpl implements CotizacionService {

    @Override
    public Mono<CotizacionResponse> generarCotizacion(CotizacionRequest request) {
        return Mono.fromCallable(() -> {
            // Simulación de lógica de cotización
            String cotizacionId = UUID.randomUUID().toString();
            
            // Por ahora simulamos precios fijos, en una implementación real
            // consultaríamos un servicio de precios o base de datos
            var itemsCotizados = new ArrayList<ProductoCotizado>();
            BigDecimal total = BigDecimal.ZERO;
            
            for (var item : request.getProductos()) {
                // Precio simulado
                BigDecimal precio = BigDecimal.valueOf(100.00);
                var itemCotizado = new ProductoCotizado(
                    item.getProductId(),
                    item.getCantidad(),
                    precio,
                        precio.multiply(BigDecimal.valueOf(item.getCantidad())),
                        0
                );
                itemsCotizados.add(itemCotizado);
                total = total.add(itemCotizado.getSubtotal());
            }
            
            return new CotizacionResponse(cotizacionId, request.getCustomerId(), total,itemsCotizados, "PENDIENTE");
        });
    }

    @Override
    public Mono<CotizacionResponse> obtenerCotizacion(String cotizacionId) {
        return Mono.fromCallable(() -> {
            // Simulación de búsqueda de cotización
            // En una implementación real consultaríamos la base de datos
            Random random = new Random();
            return new CotizacionResponse("",random.nextLong(),BigDecimal.valueOf(100.00), new ArrayList<ProductoCotizado>(), "PENDIENTE");
        });
    }
}
