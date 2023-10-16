package com.crai.platform.blobstorage.repository;


import com.crai.platform.blobstorage.domain.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {

}
