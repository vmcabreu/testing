package com.crai.platform.blobstorage.repository;


import com.crai.platform.blobstorage.domain.Document;
import com.crai.platform.blobstorage.enums.WorkUnitTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Método para buscar documentos por workUnitId

    @Query("SELECT d FROM Document d WHERE d.workUnit.workUnitId = :workUnitId")
    List<Document> findByWorkUnit_WorkUnitId(Long workUnitId);
    @Query("SELECT d FROM Document d WHERE d.workUnit.workUnitId = :workUnitId AND d.workUnit.workUnitType = :workUnitType AND d.visible = :visible")
    List<Document> findDocumentsByWorkUnitAndTypeAndVisible(Long workUnitId, WorkUnitTypeEnum workUnitType, Boolean visible);
    @Query("SELECT d FROM Document d WHERE d.workUnit.workUnitId = :workUnitId AND d.visible = true")
    List<Document> findByWorkUnitIdAndVisibleIsTrue(Long workUnitId);
}
