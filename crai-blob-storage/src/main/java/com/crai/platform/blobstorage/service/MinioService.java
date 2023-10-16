package com.crai.platform.blobstorage.service;


import com.crai.platform.blobstorage.config.MinioConfigurationProperties;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MinioService {

	@Autowired
    private final MinioClient minioClient;
    @Autowired
    private final MinioConfigurationProperties configurationProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(MinioService.class);

    @Autowired
    public MinioService(MinioClient minioClient, MinioConfigurationProperties configurationProperties) {
        this.minioClient = minioClient;
        this.configurationProperties = configurationProperties;
    }

    /**
     * Lista todos los objetos en la raíz del bucket.
     *
     * @return Lista de objetos
     */
    public List<Item> list() {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(configurationProperties.getBucket())
                .prefix("")
                .recursive(false)
                .build();
        Iterable<Result<Item>> misObjetos = minioClient.listObjects(args);
        return getItems(misObjetos);
    }

    /**
     * Lista todos los objetos en la raíz del bucket.
     *
     * @return Lista de objetos
     */
    public List<Item> listaCompleta() {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(configurationProperties.getBucket())
                .build();
        Iterable<Result<Item>> misObjetos = minioClient.listObjects(args);
        return getItems(misObjetos);
    }

    /**
     * Lista todos los objetos con el prefijo dado en el parámetro para el bucket.
     * Simula una jerarquía de carpetas. Los objetos dentro de carpetas (es decir, todos los objetos que coinciden con el patrón {@code {prefijo}/{nombreObjeto}/...}) no se devuelven.
     *
     * @param path Prefijo de la lista de objetos buscados
     * @return Lista de objetos
     */
    public List<Item> listar(Path path) {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(configurationProperties.getBucket())
                .prefix(path.toString())
                .recursive(false)
                .build();
        Iterable<Result<Item>> misObjetos = minioClient.listObjects(args);
        return getItems(misObjetos);
    }

    /**
     * Lista todos los objetos con el prefijo dado en el parámetro para el bucket.
     * <p>
     * Se devuelven todos los objetos, incluso los que están en una carpeta.
     *
     * @param path Prefijo de la lista de objetos buscados
     * @return Lista de objetos
     */
    public List<Item> getListaCompleta(Path path) {
        ListObjectsArgs args = ListObjectsArgs.builder()
                .bucket(configurationProperties.getBucket())
                .prefix(path.toString())
                .build();
        Iterable<Result<Item>> misObjetos = minioClient.listObjects(args);
        return getItems(misObjetos);
    }

    /**
     * Método de utilidad que asigna resultados a objetos y devuelve una lista.
     *
     * @param misObjetos Iterable de resultados
     * @return Lista de objetos
     */
    private List<Item> getItems(Iterable<Result<Item>> misObjetos) {
        return StreamSupport
                .stream(misObjetos.spliterator(), true)
                .map(itemResult -> {
                    try {
                        return itemResult.get();
                    } catch (Exception e) {
                        LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
                        throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * Obtener un objeto de Minio
     *
     * @param path Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @return El objeto como un flujo de entrada (InputStream)
     * @throws MinioException si ocurre un error al obtener el objeto
     */
    public InputStream get(Path path) throws MinioException {
        try {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(path.toString())
                    .build();
            return minioClient.getObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene metadatos de un objeto de Minio
     *
     * @param path Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @return Metadatos del objeto
     * @throws MinioException si ocurre un error al obtener los metadatos del objeto
     */
    public StatObjectResponse getMetadatos(Path path) throws MinioException {
        try {
            StatObjectArgs args = StatObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(path.toString())
                    .build();
            return minioClient.statObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene metadatos para varios objetos de Minio
     *
     * @param paths Rutas de todos los objetos con prefijo. Los nombres de los objetos deben estar incluidos.
     * @return Un mapa donde todas las rutas son claves y los metadatos son valores
     */
    public Map<Object, Object> getMetadatos(Iterable<Path> paths) {
        return StreamSupport.stream(paths.spliterator(), false)
            .map(path -> {
                try {
                    StatObjectArgs args = StatObjectArgs.builder()
                            .bucket(configurationProperties.getBucket())
                            .object(path.toString())
                            .build();
                    return new HashMap.SimpleEntry<>(path, minioClient.statObject(args));
                } catch (Exception e) {
                    LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
                    throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
                }
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Obtiene un archivo de Minio y lo guarda en el archivo {@code fileName}
     *
     * @param source   Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @param fileName Nombre del archivo
     * @throws MinioException si ocurre un error al buscar el objeto
     */
    public void getAndSave(Path source, String fileName) throws MinioException {
        try {
            DownloadObjectArgs args = DownloadObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(source.toString())
                    .filename(fileName)
                    .build();
            minioClient.downloadObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }

    /**
     * Sube un archivo a Minio
     *
     * @param source  Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @param file    Archivo como un flujo de entrada
     * @param headers Encabezados adicionales para agregar al archivo. El mapa DEBE ser mutable. Todos los encabezados personalizados comenzarán con el prefijo 'x-amz-meta-' cuando se recuperen con el método {@code obtenerMetadatos()}.
     * @throws MinioException si ocurre un error al cargar el objeto
     */
    public void subir(Path source, InputStream file, Map<String, String> headers) throws
        MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .headers(headers)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }
    
    /**
     * Subir un archivo a Minio
     *
     * @param source      Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @param file        Archivo como flujo de entrada
     * @throws MinioException si ocurre un error al cargar el objeto
     */
    public void subir(Path source, InputStream file) throws
        MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .build();
            minioClient.putObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }

    /**
     * Subir un archivo a Minio
     *
     * @param source      Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @param file        Archivo como flujo de entrada
     * @param contentType Tipo MIME para el objeto
     * @param headers     Encabezados adicionales para agregar al archivo. El mapa DEBE ser mutable.
     * @throws MinioException si ocurre un error al cargar el objeto
     */
    public void subir(Path source, InputStream file, String contentType, Map<String, String> headers) throws
        MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .headers(headers)
                    .contentType(contentType)
                    .build();

            minioClient.putObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }

    /**
     * Subir un archivo a Minio
     *
     * @param source      Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @param file        Archivo como flujo de entrada
     * @param contentType Tipo MIME para el objeto
     * @throws MinioException si ocurre un error al cargar el objeto
     */
    public void subir(Path source, InputStream file, String contentType) throws
        MinioException {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(source.toString())
                    .stream(file, file.available(), -1)
                    .contentType(contentType)
                    .build();

            minioClient.putObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }

    /**
     * Subir un archivo a Minio
     * Cargar archivo más grande que el tamaño Xmx
     * @param source      Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @param file        Archivo como nombre de archivo
     * @throws MinioException si ocurre un error al cargar el objeto
     */
    public void subir(Path source, File file) throws
            MinioException {
        try {
            UploadObjectArgs args = UploadObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(source.toString())
                    .filename(file.getAbsolutePath())
                    .build();
            minioClient.uploadObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }


    // Métodos adicionales para cargar archivos según sea necesario...

    /**
     * Elimina un archivo de Minio
     *
     * @param source Ruta con prefijo al objeto. El nombre del objeto debe estar incluido.
     * @throws MinioException si ocurre un error al eliminar el objeto
     */
    public void eliminar(Path source) throws MinioException {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
                    .bucket(configurationProperties.getBucket())
                    .object(source.toString())
                    .build();
            minioClient.removeObject(args);
        } catch (Exception e) {
            LOGGER.error("Error al realizar la operación de MinIO: " + e.getMessage(), e);
            throw new RuntimeException("Error al realizar la operación de MinIO: " + e.getMessage(), e);
        }
    }
    
}
