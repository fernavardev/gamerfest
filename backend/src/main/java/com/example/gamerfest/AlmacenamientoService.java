package com.example.gamerfest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlmacenamientoService {

    private static final Set<String> TIPOS_IMAGEN = Set.of(
            "image/jpeg", "image/png", "image/webp");

    private static final Set<String> TIPOS_VIDEO = Set.of(
            "video/mp4", "video/webm");

    private final Path directorioBase;

    public AlmacenamientoService(
            @Value("${gamerfest.upload-dir:uploads}") String uploadDir)
            throws IOException {

        this.directorioBase = Path.of(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.directorioBase);
    }

    public String guardarImagen(MultipartFile archivo) {
        String contentType = contentTypeNormalizado(archivo);
        if (!TIPOS_IMAGEN.contains(contentType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de imagen no permitido: " + contentType);
        }
        return guardarArchivo(archivo);
    }

    public String guardarMedia(MultipartFile archivo) {
        String contentType = contentTypeNormalizado(archivo);
        if (!TIPOS_IMAGEN.contains(contentType)
                && !TIPOS_VIDEO.contains(contentType)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Tipo de archivo no permitido: " + contentType
                            + ". Usá JPG, PNG, WebP, MP4 o WebM.");
        }
        return guardarArchivo(archivo);
    }

    private String contentTypeNormalizado(MultipartFile archivo) {
        if (archivo == null || archivo.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El archivo es obligatorio");
        }

        String contentType = archivo.getContentType();
        if (contentType == null || contentType.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No se pudo determinar el tipo de archivo");
        }

        return contentType.toLowerCase(Locale.ROOT);
    }

    private String guardarArchivo(MultipartFile archivo) {
        String extension = extensionDesdeNombre(archivo.getOriginalFilename());
        String nombreArchivo = UUID.randomUUID() + extension;
        Path destino = directorioBase.resolve(nombreArchivo).normalize();

        if (!destino.startsWith(directorioBase)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Nombre de archivo inválido");
        }

        try {
            Files.copy(
                    archivo.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "No se pudo guardar el archivo",
                    e);
        }

        return "/uploads/" + nombreArchivo;
    }

    private String extensionDesdeNombre(String nombreOriginal) {
        if (nombreOriginal == null || !nombreOriginal.contains(".")) {
            return "";
        }
        String extension = nombreOriginal.substring(
                nombreOriginal.lastIndexOf('.')).toLowerCase(Locale.ROOT);
        if (!extension.matches("\\.[a-z0-9]{1,8}")) {
            return "";
        }
        return extension;
    }
}
