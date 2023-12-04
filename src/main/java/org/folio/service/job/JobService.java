package org.folio.service.job;

import org.folio.domain.dto.MergeJobPayload;
import org.folio.model.Job;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface JobService {
  List<Job> getAllJobs();

  MergeJobPayload getJobById(UUID jobId);

  org.folio.domain.dto.MergeJobPayloadCollection getJobs(Integer offset, Integer limit, String cql);

  Job createJob(Job job);

  Job updateJob(UUID jobId, MergeJobPayload updatedJob);

  void mergeData(MergeJobPayload mergeJobPayload);

  void deleteJob(UUID jobId);
}
