package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.nio.file.Path;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;

class WebConfigTest {

    @Test
    void deberiaConfigurarRutaDeRecursosMultimedia() throws Exception {
        ResourceHandlerRegistry registry =
                mock(ResourceHandlerRegistry.class);

        ResourceHandlerRegistration registration =
                mock(ResourceHandlerRegistration.class);

        when(registry.addResourceHandler("/uploads/**"))
                .thenReturn(registration);

        WebConfig config = new WebConfig("uploads");

        String ubicacion = Path.of("uploads")
                .toAbsolutePath()
                .normalize()
                .toUri()
                .toString();

        String esperada = ubicacion.endsWith("/")
                ? ubicacion : ubicacion + "/";

        config.addResourceHandlers(registry);

        verify(registry).addResourceHandler("/uploads/**");
        verify(registration).addResourceLocations(esperada);
    }
}