package com.arka.arkajjmunozm.domain.port.in;

import com.arka.arkajjmunozm.domain.port.in.model.InventarioResult;
import com.arka.arkajjmunozm.domain.port.in.model.SolicitarInventarioCommand;

import java.util.concurrent.CompletableFuture;

public interface ISolicitarInventarioUseCase {
    CompletableFuture<InventarioResult> solicitarInventario(SolicitarInventarioCommand command);
}
