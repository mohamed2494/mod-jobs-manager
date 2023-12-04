package org.folio.service.job;

import org.folio.domain.dto.MergeJobPayload;
import org.folio.factory.MergeProducerFactory;
import org.folio.mapper.MergeJobMapper;
import org.folio.model.Job;
import org.folio.repository.JobRepository;
import org.folio.service.merge.AbstractMergeProducerService;
import org.folio.spring.data.OffsetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
  private final MergeProducerFactory mergeProducerFactory;


  @Autowired
  public JobServiceImpl(JobRepository jobRepository, MergeProducerFactory mergeProducerFactory) {
    this.jobRepository = jobRepository;
    this.mergeProducerFactory = mergeProducerFactory;
  }

  public List<Job> getAllJobs() {
    return jobRepository.findAll();
  }

  public MergeJobPayload getJobById(UUID jobId) {
    Optional<Job> optionalJob = jobRepository.findById(jobId);

    return optionalJob.map(MergeJobMapper::mapEntityToDto).orElse(null);
  }


  @Override
  public List<MergeJobPayload> getJobs(Integer offset, Integer limit, String cql) {

    Pageable pageable = PageRequest.of(offset / limit, limit);

    boolean isBlank = isBlank(cql);

    Page<Job> mergeJobPayloads = isBlank
      ? jobRepository.findAll(pageable)
      : jobRepository.findByCQL(cql, new OffsetRequest(offset, limit));

    return mergeJobPayloads.getContent().stream()
      .map(MergeJobMapper::mapEntityToDto)
      .collect(Collectors.toList());
  }

  public Job createJob(Job job) {

    return jobRepository.save(job);
  }


  public Job updateJob(UUID jobId, MergeJobPayload updatedJob) {

    return jobRepository.findById(jobId)
      .map(existingJob -> updateJobFields(existingJob, updatedJob))
      .map(jobRepository::save)
      .orElse(null);

  }

  public void mergeData(MergeJobPayload mergeJobPayload) {

    String type = mergeJobPayload.getType();
    String sourceData = mergeJobPayload.getSource();
    String destinationData = mergeJobPayload.getDestination();

    // Use the factory to get the appropriate merge producer
    AbstractMergeProducerService mergeProducerService = this.mergeProducerFactory.createMergeProducer(type);

    mergeProducerService.produceMergeEvent(sourceData, destinationData);

  }

  private Job updateJobFields(Job existingJob, MergeJobPayload updatedJob) {

    MergeJobPayload existingPayload = existingJob.getPayload();

    if (updatedJob.getStatus() != null)
      existingPayload.setStatus(updatedJob.getStatus());

    if (updatedJob.getSource() != null)
      existingPayload.setSource(updatedJob.getSource());

    if (updatedJob.getDestination() != null)
      existingPayload.setDestination(updatedJob.getDestination());

    existingJob.setPayload(existingPayload);

    return existingJob;
  }


  public void deleteJob(UUID jobId) {
    jobRepository.deleteById(jobId);
  }
}
