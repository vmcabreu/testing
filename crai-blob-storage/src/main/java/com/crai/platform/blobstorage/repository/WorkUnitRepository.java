package com.crai.platform.blobstorage.repository;


import com.crai.platform.blobstorage.domain.WorkUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkUnitRepository extends JpaRepository<WorkUnit, Long> {
}
