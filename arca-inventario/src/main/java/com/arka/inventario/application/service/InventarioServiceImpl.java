package com.arka.inventario.application.service;

import com.arka.inventario.domain.model.InventarioResult;
import com.arka.inventario.domain.model.SolicitarInventarioCommand;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InventarioServiceImpl implements InventarioService{
    @Override
    public CompletableFuture<InventarioResult> solicitarInventario(SolicitarInventarioCommand command) {
        // Simula la cantidad disponible (ejemplo: productoId * 5)
        int cantidadDisponible = (command.getProductId() % 10 + 1) * 10;
        InventarioResult result = InventarioResult.builder()
                .productoId(String.valueOf(command.getProductId()))
                .cantidadDisponible(cantidadDisponible)
                .build();
        return CompletableFuture.completedFuture(result);
    }
}
