package com.example.gamerfest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.nio.file.Path;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class AlmacenamientoServiceTest {

    @TempDir
    Path directorioTemporal;

    @Test
    void deberiaRechazarArchivosQueNoSeanImagenes() throws Exception {
        AlmacenamientoService service =
                new AlmacenamientoService(directorioTemporal.toString());

        MockMultipartFile archivo = new MockMultipartFile(
                "archivo", "documento.pdf", "application/pdf",
                new byte[]{1, 2, 3});

        ResponseStatusException error = assertThrows(
                ResponseStatusException.class,
                () -> service.guardarImagen(archivo));

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
    }
}