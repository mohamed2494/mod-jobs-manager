package org.folio.controller;


import org.folio.domain.dto.MergeJobPayload;

import org.folio.factory.MergeProducerFactory;
import org.folio.model.Job;
import org.folio.service.merge.AbstractMergeProducerService;
import org.folio.service.job.JobService;
import org.folio.service.merge.TypeAMergeProducerService;
import org.folio.service.merge.TypeBMergeProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class MergeJobController {


  private final TypeAMergeProducerService typeAMergeService;
  private final TypeBMergeProducerService typeBMergeService;

  private final MergeProducerFactory mergeProducerFactory;
  private final JobService jobService;


  @Autowired
  public MergeJobController(TypeAMergeProducerService typeAMergeService, TypeBMergeProducerService typeBMergeService, MergeProducerFactory mergeProducerFactory, JobService jobService) {
    this.typeAMergeService = typeAMergeService;
    this.typeBMergeService = typeBMergeService;
    this.mergeProducerFactory = mergeProducerFactory;
    this.jobService = jobService;

  }


  @GetMapping("/mergeJob/{id}")
  public ResponseEntity<MergeJobPayload> getMergeJob(@PathVariable String id) {
    MergeJobPayload job = jobService.getJobById(UUID.fromString(id));
    return job == null
      ? ResponseEntity.notFound().build()
      : ResponseEntity.ok(job);
  }

  @DeleteMapping("/mergeJob/{id}")
  public ResponseEntity<String> deleteMergeJob(@PathVariable String id) {

    jobService.deleteJob(UUID.fromString(id));

    return ResponseEntity.ok("Merge job with ID " + id + " deleted successfully");
  }

  @PutMapping("/mergeJob/{id}")
  public ResponseEntity<String> updateMergeJob(@PathVariable String id, MergeJobPayload mergeJobPayload) {

    jobService.updateJob(UUID.fromString(id),mergeJobPayload);

    return ResponseEntity.ok("Merge job with ID " + id + " updated successfully");
  }

  @PostMapping("/mergeJob")
  public String mergeData(@RequestBody MergeJobPayload mergeRequest) {
    String type = mergeRequest.getType();
    String sourceData = mergeRequest.getSource();
    String destinationData = mergeRequest.getDestination();


    // Use the factory to get the appropriate merge producer
    AbstractMergeProducerService mergeProducerService = this.mergeProducerFactory.createMergeProducer(type);

    mergeProducerService.produceMergeEvent(sourceData, destinationData);

    return "Merge event processed successfully";
  }


  @GetMapping("/mergeJob")
  public ResponseEntity<List<MergeJobPayload>> getMergeJobs(Integer offset,
                                                      Integer limit,
                                                      @Valid String query) {
    List<MergeJobPayload> mergeJobPayloads = jobService.getJobs(offset, limit,query);


    return mergeJobPayloads == null
      ? ResponseEntity.notFound().build()
      : ResponseEntity.ok(mergeJobPayloads);
  }
  @PostMapping("/mergeJob/typeA")
  public String typeA(@RequestBody MergeJobPayload mergeRequest) {

    String sourceData = mergeRequest.getSource();
    String destinationData = mergeRequest.getDestination();

    this.typeAMergeService.produceMergeEvent(sourceData, destinationData);

    return "Merge event A processed successfully";
  }

  @PostMapping("/mergeJob/typeB")
  public String typeB(@RequestBody MergeJobPayload mergeRequest) {

    String sourceData = mergeRequest.getSource();
    String destinationData = mergeRequest.getDestination();

    this.typeBMergeService.produceMergeEvent(sourceData, destinationData);

    return "Merge event B processed successfully";
  }
}
