package com.arka.arkajjmunozm.application.usecase;


import com.arka.arkajjmunozm.domain.port.out.GestorSolicitudesServicePort;
import com.arka.arkajjmunozm.infraestructure.adapter.out.microservice.dto.SolicitudRequestDto;
import com.arka.arkajjmunozm.infraestructure.adapter.out.microservice.dto.SolicitudResponseDto;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.entity.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SolicitudApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(SolicitudApplicationService.class);

    private final GestorSolicitudesServicePort gestorSolicitudesServicePort;

    public SolicitudApplicationService(GestorSolicitudesServicePort gestorSolicitudesServicePort) {
        this.gestorSolicitudesServicePort = gestorSolicitudesServicePort;
    }

    /**
     * Crea una solicitud de productos
     */
    public CompletableFuture<SolicitudResponseDto> crearSolicitudProductos(Long customerId, List<ProductEntity> productos,
                                                                           String tipoSolicitud, String prioridad, String observaciones) {
        try {
            logger.info("Creando solicitud para cliente: {} con {} productos", customerId, productos.size());

            List<SolicitudRequestDto.ProductoSolicitudDto> productosDto = productos.stream()
                    .map(producto -> new SolicitudRequestDto.ProductoSolicitudDto(
                            (long)producto.getId(),
                            producto.getDescription(),
                            1, // Cantidad por defecto
                            producto.getDescription()
                    ))
                    .collect(Collectors.toList());

            SolicitudRequestDto request = new SolicitudRequestDto(customerId, tipoSolicitud, prioridad, productosDto, observaciones);

            return gestorSolicitudesServicePort.crearSolicitud(request)
                    .whenComplete((result, throwable) -> {
                        if (throwable != null) {
                            logger.error("Error al crear solicitud: {}", throwable.getMessage());
                        } else {
                            logger.info("Solicitud creada exitosamente: {}", result.getSolicitudId());
                        }
                    });

        } catch (Exception ex) {
            logger.error("Error al procesar creación de solicitud: {}", ex.getMessage());
            CompletableFuture<SolicitudResponseDto> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(ex);
            return failedFuture;
        }
    }

    /**
     * Crea una solicitud de cotización
     */
    public CompletableFuture<SolicitudResponseDto> crearSolicitudCotizacion(Long customerId, List<ProductEntity> productos, String observaciones) {
        return crearSolicitudProductos(customerId, productos, "COTIZACION", "MEDIA", observaciones);
    }

    /**
     * Crea una solicitud de información
     */
    public CompletableFuture<SolicitudResponseDto> crearSolicitudInformacion(Long customerId, List<ProductEntity> productos, String observaciones) {
        return crearSolicitudProductos(customerId, productos, "INFORMACION", "BAJA", observaciones);
    }

    /**
     * Crea una solicitud urgente
     */
    public CompletableFuture<SolicitudResponseDto> crearSolicitudUrgente(Long customerId, List<ProductEntity> productos, String observaciones) {
        return crearSolicitudProductos(customerId, productos, "URGENTE", "ALTA", observaciones);
    }

    /**
     * Obtiene solicitudes de un cliente
     */
    public CompletableFuture<List<SolicitudResponseDto>> obtenerSolicitudesCliente(Long customerId) {
        logger.info("Obteniendo solicitudes para cliente: {}", customerId);
        return gestorSolicitudesServicePort.obtenerSolicitudesPorCliente(customerId);
    }

    /**
     * Obtiene una solicitud específica
     */
    public CompletableFuture<SolicitudResponseDto> obtenerSolicitud(String solicitudId) {
        logger.info("Obteniendo solicitud: {}", solicitudId);
        return gestorSolicitudesServicePort.obtenerSolicitud(solicitudId);
    }

    /**
     * Obtiene solicitudes por estado
     */
    public CompletableFuture<List<SolicitudResponseDto>> obtenerSolicitudesPorEstado(String estado) {
        logger.info("Obteniendo solicitudes por estado: {}", estado);
        return gestorSolicitudesServicePort.obtenerSolicitudesPorEstado(estado);
    }

    /**
     * Actualiza el estado de una solicitud
     */
    public CompletableFuture<SolicitudResponseDto> actualizarEstadoSolicitud(String solicitudId, String nuevoEstado) {
        logger.info("Actualizando estado de solicitud {} a: {}", solicitudId, nuevoEstado);
        return gestorSolicitudesServicePort.actualizarEstadoSolicitud(solicitudId, nuevoEstado);
    }

    /**
     * Verifica disponibilidad del servicio
     */
    public CompletableFuture<Boolean> verificarDisponibilidadServicio() {
        return gestorSolicitudesServicePort.isServiceAvailable();
    }
}
