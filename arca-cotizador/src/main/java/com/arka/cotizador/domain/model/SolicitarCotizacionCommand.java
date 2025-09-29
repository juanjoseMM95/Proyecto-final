package com.arka.cotizador.domain.model;


import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SolicitarCotizacionCommand {
    Long customerId;
    List<ProductoSolicitud> productos;

    @Value
    @Builder
    public static class ProductoSolicitud {
        int productId;
        int cantidad;
    }

}
