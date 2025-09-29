package com.arka.arkajjmunozm.domain.port.out.dto;

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
