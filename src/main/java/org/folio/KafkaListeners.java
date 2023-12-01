package org.folio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.folio.domain.dto.MergeJobPayload;
import org.folio.service.job.JobStatusService;
import org.folio.spring.FolioModuleMetadata;
import org.folio.util.TenantContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

  private static final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);
  private final ObjectMapper objectMapper;
  private final JobStatusService jobStatusService;
  private final FolioModuleMetadata moduleMetadata;


  @Autowired
  public KafkaListeners(ObjectMapper objectMapper, JobStatusService jobStatusService, FolioModuleMetadata moduleMetadata) {
    this.objectMapper = objectMapper;
    this.jobStatusService = jobStatusService;
    this.moduleMetadata = moduleMetadata;
  }

  @KafkaListener(
    topics = "merge-events-status",
    groupId = "groupId"
  )
  void listener(String mergeEventJson) {

    logger.info("Received merge event Status JSON: {}", mergeEventJson);
    // Convert the JSON string to MergeEvent manually (for troubleshooting)
    try {
      MergeJobPayload mergeEvent = objectMapper.readValue(mergeEventJson, MergeJobPayload.class);

      TenantContextUtils.runInFolioContext(TenantContextUtils.getFolioExecutionContextFromTenant(mergeEvent.getTenantId(), moduleMetadata),
        () -> this.jobStatusService.updateJobStatus(mergeEvent.getMergeId(), mergeEvent.getStatus()));

      logger.info("Converted MergeEvent finishes: {}", mergeEvent.getMergeId());

    } catch (JsonProcessingException e) {
      logger.error("Error converting JSON to MergeEvent: {}", e.getMessage());
    }
  }
}
