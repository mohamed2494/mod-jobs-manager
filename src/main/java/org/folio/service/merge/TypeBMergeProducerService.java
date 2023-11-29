package org.folio.service.merge;

import org.folio.domain.dto.MergeJobPayload;
import org.folio.service.job.JobService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TypeBMergeProducerService extends AbstractMergeProducerService {

  private final String TYPE = String.valueOf(MergeConstants.MergeType.TYPE_B);
  private final String mergeTopic = MergeConstants.MERGE_TOPIC;

  public TypeBMergeProducerService(KafkaTemplate<String, Object> kafkaTemplate, JobService jobService) {
    super(kafkaTemplate, jobService);
  }

  @Override
  protected String getTYPE() {
    return this.TYPE;
  }

  @Override
  protected MergeJobPayload getMergeData(UUID uuid, String sourceData, String destinationData) {

    System.out.println("TypeBMergeProducerService" + this.TYPE);

    System.out.println("TypeBMergeProducerService type" + getTYPE());
    // Business logic depend on the type
    return new MergeJobPayload().mergeId(uuid).source(sourceData).destination(destinationData).type(TYPE);
  }

}