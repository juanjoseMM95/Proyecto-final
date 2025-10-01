package com.arka.arkajjmunozm.domain.port.out;

import com.arka.arkajjmunozm.domain.port.out.dto.InventarioRequest;
import com.arka.arkajjmunozm.domain.port.out.dto.InventarioResponse;

import java.util.concurrent.CompletableFuture;

public interface IInventarioServicePort {
    CompletableFuture<InventarioResponse> solicitarInventario(InventarioRequest request);
}
