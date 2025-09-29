package com.arka.cotizador.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class CotizacionResult {
    String cotizacionId;
    Long customerId;
    BigDecimal total;
    List<ProductoCotizado> productos;
    String estado;

    @Value
    @Builder
    public static class ProductoCotizado {
        int productId;
        int cantidad;
        BigDecimal precioUnitario;
        BigDecimal subtotal;
        int descuentoAplicado;
    }
}
