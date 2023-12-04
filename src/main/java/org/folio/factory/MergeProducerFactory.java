package org.folio.factory;

import org.folio.service.merge.AbstractMergeProducerService;
import org.folio.service.merge.TypeAMergeProducerService;
import org.folio.service.merge.TypeBMergeProducerService;
import org.folio.service.merge.TypeCMergeProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MergeProducerFactory {
  private final TypeAMergeProducerService typeAMergeService;
  private final TypeBMergeProducerService typeBMergeService;

  private final TypeCMergeProducerService typeCMergeService;


  @Autowired
  public MergeProducerFactory(TypeAMergeProducerService typeAMergeService, TypeBMergeProducerService typeBMergeService, TypeCMergeProducerService typeCMergeProducerService) {
    this.typeAMergeService = typeAMergeService;
    this.typeBMergeService = typeBMergeService;
    this.typeCMergeService = typeCMergeProducerService;

  }

  public AbstractMergeProducerService createMergeProducer(String type) {

    if ("TypeA".equalsIgnoreCase(type)) {
      return typeAMergeService;
    } else if ("TypeB".equalsIgnoreCase(type)) {
      return typeBMergeService;
    }
    // Handle unknown merge types or return a default producer
    return typeCMergeService;
  }
}
