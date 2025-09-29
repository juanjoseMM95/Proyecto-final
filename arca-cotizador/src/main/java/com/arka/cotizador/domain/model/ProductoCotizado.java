package com.arka.cotizador.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoCotizado {
    private int productId;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private int descuentoAplicado;
}
