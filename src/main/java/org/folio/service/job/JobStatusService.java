package org.folio.service.job;

import java.util.UUID;

public interface JobStatusService {
  int updateJobStatus(UUID jobId, String status);
}
