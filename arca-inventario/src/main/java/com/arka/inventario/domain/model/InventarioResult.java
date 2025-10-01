package com.arka.inventario.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InventarioResult {
    private String productoId;
    private Integer cantidadDisponible;
}
