package com.arka.arkajjmunozm.domain.port.in;
import com.arka.arkajjmunozm.domain.port.in.model.CotizacionResult;
import com.arka.arkajjmunozm.domain.port.in.model.SolicitarCotizacionCommand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ISolicitarCotizacionUseCase {
    CompletableFuture<CotizacionResult> solicitarCotizacion(SolicitarCotizacionCommand command);

}
