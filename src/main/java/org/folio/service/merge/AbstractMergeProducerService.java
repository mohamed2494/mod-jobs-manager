package org.folio.service.merge;

import org.folio.domain.dto.MergeJobPayload;
import org.folio.model.Job;
import org.folio.repository.JobRepository;
import org.folio.service.job.JobService;
import org.folio.spring.FolioExecutionContext;
import org.folio.util.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public abstract class AbstractMergeProducerService {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final JobRepository jobRepository;
  private final String mergeTopic = MergeConstants.MERGE_TOPIC;

  @Autowired
  private FolioExecutionContext folioExecutionContext;

  @Autowired
  public AbstractMergeProducerService(KafkaTemplate<String, Object> kafkaTemplate, JobRepository jobRepository) {
    this.kafkaTemplate = kafkaTemplate;
    this.jobRepository = jobRepository;
  }

  public void produceMergeEvent(String sourceData, String destinationData) {

    UUID uuid = UUID.randomUUID();

    MergeJobPayload mergeEvent = getMergeData(uuid, sourceData, destinationData);


    mergeEvent.setStatus(String.valueOf(Job.Status.NEW));
    mergeEvent.setTenantId(folioExecutionContext.getTenantId());
    // Save the job before producing the merge event
    Job job = new Job(getTYPE(), mergeEvent);
    jobRepository.save(job);

    // publish kafka event
    kafkaTemplate.send(getTopic(), mergeEvent);
  }

  protected String getTopic() {


    return KafkaUtils.getTenantTopicName(this.mergeTopic, folioExecutionContext.getTenantId());

  }

  protected abstract String getTYPE();

  protected abstract MergeJobPayload getMergeData(UUID uuid, String sourceData, String destinationData);

}
