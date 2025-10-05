package com.arka.inventario;

import com.arka.security.config.ArkaSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(ArkaSecurityAutoConfiguration.class)
public class ArcaInventarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArcaInventarioApplication.class, args);
    }
}
