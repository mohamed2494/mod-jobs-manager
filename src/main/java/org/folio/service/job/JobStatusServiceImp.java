package org.folio.service.job;

import org.folio.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobStatusServiceImp implements JobStatusService {

  private final JobRepository jobRepository;

  @Autowired
  public JobStatusServiceImp(JobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  @Override
  public int updateJobStatus(UUID jobId, String status) {

    return jobRepository.updateStatusById(jobId, status);
  }

}
