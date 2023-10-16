package com.crai.platform.blobstorage.service;

import com.crai.platform.blobstorage.domain.Document;
import com.crai.platform.blobstorage.domain.WorkUnit;
import com.crai.platform.blobstorage.enums.WorkUnitTypeEnum;
import com.crai.platform.blobstorage.repository.DocumentRepository;
import com.crai.platform.blobstorage.repository.WorkUnitRepository;
import io.minio.errors.MinioException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class WorkDocumentService {

    private final WorkUnitRepository workUnitRepository;
    private final DocumentRepository documentRepository;
    private final MinioService minioService;

    @Autowired
    public WorkDocumentService(
            WorkUnitRepository workUnitRepository,
            DocumentRepository documentRepository,
            MinioService minioService
    ) {
        this.workUnitRepository = workUnitRepository;
        this.documentRepository = documentRepository;
        this.minioService = minioService;
    }

    @Transactional
    public void saveDocumentToWorkUnit(Long workUnitId, String fileName, String filePath, InputStream fileInputStream) throws MinioException {
        WorkUnit workUnit = workUnitRepository.findById(workUnitId).orElse(null);
        if (workUnit == null) {
            throw new IllegalArgumentException("Unidad de trabajo no encontrada con ID: " + workUnitId);
        }

        // Sube el archivo al Minio
        Path source = Paths.get(filePath);
        minioService.subir(source, fileInputStream);

        // Crea un nuevo documento y guarda en la base de datos
        Document document = new Document();
        document.setFileName(fileName);
        document.setFilePath(filePath);
        document.setWorkUnit(workUnit);
        documentRepository.save(document);
    }


    @Transactional
    public void updateDocument(Long documentId, String nuevoFileName, String nuevoFilePath, Boolean isVisible) {
        Document document = documentRepository.findById(documentId).orElse(null);
        if (document == null) {
            throw new IllegalArgumentException("Documento no encontrado con ID: " + documentId);
        }

        // Actualiza la información del documento
        if (StringUtils.isNotEmpty(nuevoFileName)) {
            document.setFileName(nuevoFileName);
        }
        if (StringUtils.isNotEmpty(nuevoFilePath)) {
            document.setFilePath(nuevoFilePath);
        }
        if (isVisible != null && document.getVisible().compareTo(isVisible) != 0) {
            document.setVisible(isVisible);
        }

        documentRepository.save(document);
    }

    @Transactional
    public void deleteDocument(Long documentId) throws MinioException {
        Document document = documentRepository.findById(documentId).orElse(null);
        if (document == null) {
            throw new IllegalArgumentException("Documento no encontrado con ID: " + documentId);
        }
        // Elimina el documento de la base de datos de forma logica
        document.setVisible(Boolean.FALSE);
        documentRepository.save(document);
    }

    @Transactional
    public void deleteDocumentsByWorkUnit(Long workUnitId) throws MinioException {
        List<Document> documents = documentRepository.findByWorkUnit_WorkUnitId(workUnitId);
        if(!CollectionUtils.isEmpty(documents)){
            documents.forEach(document->document.setVisible(false));
            documentRepository.saveAll(documents);
        }
    }

    @Transactional(readOnly = true)
    public List<Document> listDocumentsByWorkUnitAndTypeVisible(Long workUnitId, WorkUnitTypeEnum workUnitType) {
        List<Document> documents = documentRepository.findDocumentsByWorkUnitAndTypeAndVisible(
                workUnitId,
                workUnitType,
                Boolean.TRUE
        );
        return documents;
    }


    @Transactional(readOnly = true)
    public List<Document> listDocumentsByWorkUnit(Long workUnitId) {
        List<Document> documents = documentRepository.findByWorkUnitIdAndVisibleIsTrue(workUnitId);
        return documents;
    }
}

