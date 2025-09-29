package com.arka.cotizador.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CotizacionRequest {
    private Long customerId;
    private List<ProductoCotizacion> productos;
}
