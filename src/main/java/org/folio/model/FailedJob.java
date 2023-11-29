package org.folio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "failed_jobs")
@AllArgsConstructor
@NoArgsConstructor
public class FailedJob {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "job_id", nullable = false)
  private Job job;

  @Column(name = "failure_reason")
  private String failureReason;

}
