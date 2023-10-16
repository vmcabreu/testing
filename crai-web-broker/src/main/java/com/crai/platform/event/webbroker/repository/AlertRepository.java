package com.crai.platform.event.webbroker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.crai.platform.event.webbroker.dto.AlertEntity;

@Repository
public interface AlertRepository extends PagingAndSortingRepository<AlertEntity, String> {
   
   public Page<AlertEntity> findAllByOperatorUser(String operatorUser, Pageable pageable);
   
   public AlertEntity findById(String id);
   
   public AlertEntity save(AlertEntity alert);
   
}
