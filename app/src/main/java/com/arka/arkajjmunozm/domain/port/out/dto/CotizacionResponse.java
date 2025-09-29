package com.arka.arkajjmunozm.domain.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CotizacionResponse {
    private String cotizacionId;
    private Long customerId;
    private BigDecimal total;
    private List<ProductoCotizado> productos;
    private String estado;
}
