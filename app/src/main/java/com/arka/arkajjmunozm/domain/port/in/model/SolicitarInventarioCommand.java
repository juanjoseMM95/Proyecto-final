package com.arka.arkajjmunozm.domain.port.in.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SolicitarInventarioCommand {
    int productId;
}
