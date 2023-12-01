package org.folio.mapper;

import org.folio.domain.dto.MergeJobPayload;
import org.folio.model.Job;

public class MergeJobMapper {

  public static MergeJobPayload mapEntityToDto(Job job) {


    MergeJobPayload mergeJobPayload = new MergeJobPayload().mergeId(job.getId())
      .source(job.getPayload().getSource()).destination(job.getPayload().getDestination())
      .type(job.getType());

    mergeJobPayload.setStatus(job.getStatus());

    return mergeJobPayload;
  }

  public static Job mapDtoToEntity(MergeJobPayload mergeJobPayload) {


    return new Job(mergeJobPayload.getType(), mergeJobPayload);
  }
}
