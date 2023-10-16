package com.crai.platform.agenda.repository;

import com.crai.platform.agenda.domain.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda,Long>, JpaSpecificationExecutor<Agenda> {
}
