package com.arka.inventario.application.service;

import com.arka.inventario.domain.model.InventarioResult;
import com.arka.inventario.domain.model.SolicitarInventarioCommand;
import java.util.concurrent.CompletableFuture;

public interface InventarioService {
    CompletableFuture<InventarioResult> solicitarInventario(SolicitarInventarioCommand command);
}
