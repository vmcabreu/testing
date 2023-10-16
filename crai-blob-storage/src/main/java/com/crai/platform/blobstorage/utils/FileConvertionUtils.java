package com.crai.platform.blobstorage.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileConvertionUtils {


    /**
     * Convierte un objeto MultipartFile en un InputStream.
     *
     * @param multipartFile El archivo a convertir.
     * @return InputStream del archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public InputStream multipartFileToInputStream(MultipartFile multipartFile) throws IOException {
        return multipartFile.getInputStream();
    }

    /**
     * Genera un objeto Path a partir de un nombre de archivo.
     *
     * @param fileName Nombre del archivo.
     * @return Objeto Path.
     */
    public Path generatePathFromFileName(String fileName) {
        return Paths.get(fileName);
    }
}

