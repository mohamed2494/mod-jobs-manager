package org.folio.repository;

import org.folio.model.Job;
import org.folio.spring.cql.JpaCqlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface JobRepository extends JpaCqlRepository<Job, UUID> {

  @Transactional
  @Modifying
  @Query("UPDATE Job j SET j.status = :status WHERE j.id = :id")
  int updateStatusById(@Param("id") UUID id, @Param("status") String status);

}

