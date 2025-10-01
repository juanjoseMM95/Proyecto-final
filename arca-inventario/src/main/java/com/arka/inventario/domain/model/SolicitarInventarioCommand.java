package com.arka.inventario.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SolicitarInventarioCommand {
    int productId;
}
