package org.folio.service.merge;

import org.folio.domain.dto.MergeJobPayload;
import org.folio.model.Job;
import org.folio.service.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public abstract class AbstractMergeProducerService {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final JobService jobService;
  private final String mergeTopic = MergeConstants.MERGE_TOPIC;

  @Autowired
  public AbstractMergeProducerService(KafkaTemplate<String, Object> kafkaTemplate, JobService jobService) {
    this.kafkaTemplate = kafkaTemplate;
    this.jobService = jobService;
  }

  public void produceMergeEvent(String sourceData, String destinationData) {

    UUID uuid = UUID.randomUUID();

    MergeJobPayload mergeEvent = getMergeData(uuid, sourceData, destinationData);


    mergeEvent.setStatus(String.valueOf(Job.Status.PENDING));
    // Save the job before producing the merge event
    Job job = new Job(getTYPE(), mergeEvent);
    jobService.createJob(job);

    // publish kafka event
    kafkaTemplate.send(getTopic(), mergeEvent);
  }

  protected String getTopic() {
    return this.mergeTopic;
  }

  protected abstract String getTYPE();

  protected abstract MergeJobPayload getMergeData(UUID uuid, String sourceData, String destinationData);

}
