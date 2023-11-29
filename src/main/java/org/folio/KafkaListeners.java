package org.folio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.folio.domain.dto.MergeJobPayload;
import org.folio.service.job.JobStatusService;
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


  @Autowired
  public KafkaListeners(ObjectMapper objectMapper, JobStatusService jobStatusService) {
    this.objectMapper = objectMapper;
    this.jobStatusService = jobStatusService;
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
      logger.info("Converted MergeEvent: {}", mergeEvent.getMergeId());
      this.jobStatusService.updateJobStatus(mergeEvent.getMergeId(), mergeEvent.getStatus());
    } catch (JsonProcessingException e) {
      logger.error("Error converting JSON to MergeEvent: {}", e.getMessage());
    }
  }
}
