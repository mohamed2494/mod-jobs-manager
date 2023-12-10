package org.folio.model;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.folio.domain.dto.MergeJobPayload;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;


import java.sql.Timestamp;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  private final UUID id;
  @Column(name = "type", nullable = false)
  private final String type;
  @Type(JsonBinaryType.class)
  @Column(name = "payload", columnDefinition = "JSONB")
  private MergeJobPayload payload;
  @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(50)")
  private String status;
  @CreationTimestamp
  @Column(name = "created_at", updatable = false, nullable = false)
  private Timestamp createdAt;
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private Timestamp updatedAt;
  public Job() {
    this.payload = null;
    this.type = "Type_A";
    this.id = UUID.randomUUID();
  }

  public Job(String type, MergeJobPayload payload) {
    // It's better to generate UUID here
    // this.id = UUID.randomUUID();
    this.id = payload.getMergeId();
    this.type = type;
    this.payload = payload;

    this.status = payload.getStatus();
  }

  public UUID getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public MergeJobPayload getPayload() {
    return payload;
  }

  public void setPayload(MergeJobPayload payload) {
    this.payload = payload;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public String toString() {
    return "Job{" +
      "id=" + id +
      ", type='" + type + '\'' +
      ", status='" + status + '\'' +
      ", payload=" + payload +
      ", createdAt=" + createdAt +
      ", updatedAt=39ba6303-087c-4d46-8f6f-b852167eb8c3" + updatedAt +
      '}';
  }

  public enum Status {NEW, PENDING, COMPLETED, FAILED}
}
