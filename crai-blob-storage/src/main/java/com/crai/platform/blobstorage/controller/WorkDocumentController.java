package com.crai.platform.blobstorage.controller;

import com.crai.platform.blobstorage.domain.Document;
import com.crai.platform.blobstorage.enums.WorkUnitTypeEnum;
import com.crai.platform.blobstorage.service.WorkDocumentService;
import com.crai.platform.blobstorage.utils.FileConvertionUtils;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class WorkDocumentController {

    private final WorkDocumentService workDocumentService;
    private final FileConvertionUtils fileConvertionUtils;

    @Autowired
    public WorkDocumentController(WorkDocumentService workDocumentService, FileConvertionUtils fileConvertionUtils) {
        this.workDocumentService = workDocumentService;
        this.fileConvertionUtils = fileConvertionUtils;
    }

    @PostMapping("/{workUnitId}")
    public ResponseEntity<?> uploadDocument(
            @PathVariable Long workUnitId,
            @RequestParam String fileName,
            @RequestParam MultipartFile file
    ) {
        try {
            InputStream fileInputStream = fileConvertionUtils.multipartFileToInputStream(file);
            String filePath = fileConvertionUtils.generatePathFromFileName(fileName).toString();
            workDocumentService.saveDocumentToWorkUnit(workUnitId, fileName, filePath, fileInputStream);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException | MinioException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar el documento: " + e.getMessage());
        }
    }

    @GetMapping("/listByWorkUnitAndType")
    public ResponseEntity<List<Document>> listDocumentsByWorkUnitAndType(
            @RequestParam Long workUnitId,
            @RequestParam WorkUnitTypeEnum workUnitType
    ) {
        List<Document> documents = workDocumentService.listDocumentsByWorkUnitAndTypeVisible(workUnitId, workUnitType);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/listByWorkUnit")
    public ResponseEntity<List<Document>> listDocumentsByWorkUnit(@RequestParam Long workUnitId) {
        List<Document> documents = workDocumentService.listDocumentsByWorkUnit(workUnitId);
        return ResponseEntity.ok(documents);
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<?> updateDocument(
            @PathVariable Long documentId,
            @RequestParam(required = false) String fileName,
            @RequestParam(required = false) String filePath,
            @RequestParam(required = false) Boolean isVisible
    ) {
        try {
            workDocumentService.updateDocument(documentId, fileName, filePath, isVisible);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long documentId) {
        try {
            workDocumentService.deleteDocument(documentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException | MinioException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar el documento: " + e.getMessage());
        }
    }
}
