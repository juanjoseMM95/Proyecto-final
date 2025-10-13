
package com.arka.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.List;

/**
 * Filtro global de autenticación JWT para el API Gateway
 */
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final String SECRET_KEY = "ArkaSecretKeyForJWTTokenGenerationAndValidation2024!";

    // Rutas que no requieren autenticación
    private static final List<String> OPEN_API_ENDPOINTS = List.of(
            "/auth/register",
            "/auth/login",
            "/auth/refresh",
            "/actuator/health",
            "/actuator/info",
            "/eureka"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        System.out.println("=== GATEWAY FILTER DEBUG ===");
        System.out.println("Path: " + path);
        System.out.println("Method: " + request.getMethod());

        System.out.println("API Gateway - Request: " + request.getMethod() + " " + path);

        // Permitir endpoints abiertos
        if (isOpenEndpoint(path)) {
            System.out.println("Open endpoint - skipping validation");
            return chain.filter(exchange);
        }

        // Extraer token del header Authorization
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("Auth Header: " + (authHeader != null ? "Bearer ***" : "NULL"));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Missing or invalid Authorization header");
            return onError(exchange, "Token de acceso requerido", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        try {
            // Validar token
            Claims claims = validateToken(token);
            System.out.println("Token valid for user: " + claims.getSubject());
            System.out.println("User permissions: " + claims.get("permissions"));

            // Verificar permisos según la ruta
            if (!hasRequiredPermission(path, claims)) {
                System.out.println("Insufficient permissions for path: " + path);
                return onError(exchange, "Permisos insuficientes", HttpStatus.FORBIDDEN);
            }
            System.out.println("Permission check passed - forwarding to microservice");
            // Propagar información del usuario a los microservicios
            ServerHttpRequest modifiedRequest = request.mutate()
                    .headers(headers -> {
                        headers.set("X-User-Id", claims.get("userId").toString());
                        headers.set("X-User-Username", claims.getSubject());
                        headers.set("X-User-Role", claims.get("role").toString());
                    })
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (Exception e) {
            return onError(exchange, "Token inválido: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private Claims validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean hasRequiredPermission(String path, Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) claims.get("permissions");

        if (permissions == null) return false;

        // Definir permisos requeridos por ruta
        if (path.contains("/api/inventario")) {
            return permissions.contains("CALC_READ");
        }

        if (path.contains("/api/cotizador")) {
            return permissions.contains("QUOTE_READ");
        }

        return true; // Por defecto permitir si no hay regla específica
    }

    private boolean isOpenEndpoint(String path) {
        return OPEN_API_ENDPOINTS.stream()
                .anyMatch(openPath -> path.contains(openPath));
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        String errorResponse = String.format(
                "{\"error\":\"%s\",\"message\":\"%s\",\"status\":%d}",
                httpStatus.getReasonPhrase(),
                err,
                httpStatus.value()
        );

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(errorResponse.getBytes()))
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}