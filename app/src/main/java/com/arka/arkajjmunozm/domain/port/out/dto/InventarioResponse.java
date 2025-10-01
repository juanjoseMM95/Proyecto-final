package com.arka.arkajjmunozm.domain.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioResponse {
    private String productoId;
    private Integer cantidadDisponible;
}
