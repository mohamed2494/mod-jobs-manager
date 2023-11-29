package org.folio.service.job;

import org.folio.domain.dto.MergeJobPayload;
import org.folio.model.Job;

import java.util.List;
import java.util.UUID;

public interface JobService {
  List<Job> getAllJobs();

  MergeJobPayload getJobById(UUID jobId);
  List<MergeJobPayload> getJobs(Integer offset, Integer limit, String cql);

  Job createJob(Job job);

  Job updateJob(UUID jobId, MergeJobPayload updatedJob);

  void deleteJob(UUID jobId);
}
