package com.crai.platform.blobstorage.controller;


import com.crai.platform.blobstorage.service.MinioService;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class BlobStorageController {

	@Autowired
	private MinioService minioService;
	
	@GetMapping
	public List<Item> testMinio() throws MinioException  {
		return minioService.list();
	}
	
	@SuppressWarnings("unused")
	@GetMapping("/{object}")
	public void getObject(@PathVariable("object") String object, HttpServletResponse response) throws MinioException, IOException {
	    InputStream inputStream = minioService.get(Path.of(object));
	    InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

	    // Establece el tipo de contenido y la cabecera de adjunto.
	    response.addHeader("Content-disposition", "attachment;filename=" + object);
	    response.setContentType(URLConnection.guessContentTypeFromName(object));

	    // Copia el flujo al flujo de salida de la respuesta.
	    IOUtils.copy(inputStream, response.getOutputStream());
	    response.flushBuffer();
	}

	@PostMapping
	public void addAttachment(@RequestParam("file") MultipartFile file) {
	    Path path = Path.of(file.getOriginalFilename());
	    try {
	        minioService.subir(path, file.getInputStream(), file.getContentType());
	    } catch (MinioException e) {
	        throw new IllegalStateException("No se puede cargar el archivo en el almacenamiento interno. Por favor, inténtalo de nuevo más tarde", e);
	    } catch (IOException e) {
	        throw new IllegalStateException("No se puede leer el archivo", e);
	    }
	}

	@PutMapping("/{object}")
	public void updateObject(@PathVariable("object") String object, @RequestParam("file") MultipartFile file) {
	    Path path = Path.of(object);
	    try {
	        minioService.subir(path, file.getInputStream(), file.getContentType());
	    } catch (MinioException e) {
	        throw new IllegalStateException("No se puede actualizar el archivo en el almacenamiento interno. Por favor, inténtalo de nuevo más tarde", e);
	    } catch (IOException e) {
	        throw new IllegalStateException("No se puede leer el archivo", e);
	    }
	}

	@DeleteMapping("/{object}")
	public void deleteObject(@PathVariable("object") String object) {
	    Path path = Path.of(object);
	    try {
	        minioService.eliminar(path);
	    } catch (MinioException e) {
	        throw new IllegalStateException("No se puede eliminar el archivo del almacenamiento interno. Por favor, inténtalo de nuevo más tarde", e);
	    }
	}
	
}
