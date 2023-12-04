package org.folio.controller;


import org.folio.domain.dto.MergeJobPayload;

import org.folio.domain.dto.MergeJobPayloadCollection;
import org.folio.factory.MergeProducerFactory;
import org.folio.service.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class MergeJobController implements org.folio.rest.api.DefaultApi {


  private final MergeProducerFactory mergeProducerFactory;
  private final JobService jobService;


  @Autowired
  public MergeJobController(MergeProducerFactory mergeProducerFactory, JobService jobService) {
    this.mergeProducerFactory = mergeProducerFactory;
    this.jobService = jobService;

  }


  @Override
  public ResponseEntity<MergeJobPayload> getMergeJob(@PathVariable String id) {
    MergeJobPayload job = jobService.getJobById(UUID.fromString(id));
    return job == null
      ? ResponseEntity.notFound().build()
      : ResponseEntity.ok(job);
  }

  @Override
  public ResponseEntity<Void> deleteMergeJob(@PathVariable String id) {

    jobService.deleteJob(UUID.fromString(id));

    return ResponseEntity.noContent().build();

  }

  @Override
  public ResponseEntity<Void> updateMergeJob(@PathVariable String id, MergeJobPayload mergeJobPayload) {

    jobService.updateJob(UUID.fromString(id), mergeJobPayload);

    return ResponseEntity.noContent().build();

  }

  @Override
  public ResponseEntity<Void> mergeData(@RequestBody MergeJobPayload mergeRequest) {

    this.jobService.mergeData(mergeRequest);

    return ResponseEntity.noContent().build();
  }


  @Override
  public ResponseEntity<MergeJobPayloadCollection> getMergeJobs(Integer offset,
                                                                Integer limit,
                                                                @Valid String query) {
    List<MergeJobPayload> mergeJobPayloads = jobService.getJobs(offset, limit, query);


    return mergeJobPayloads == null
      ? ResponseEntity.notFound().build()
      : ResponseEntity.ok((MergeJobPayloadCollection) mergeJobPayloads);
  }

}
