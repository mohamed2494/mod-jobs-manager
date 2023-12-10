package org.folio.factory;

import org.folio.service.merge.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MergeProducerFactory {

  private final Map<String, AbstractMergeProducerService> mergeServiceMap;


  @Autowired
  public MergeProducerFactory(TypeAMergeProducerService typeAMergeService, TypeBMergeProducerService typeBMergeService, TypeCMergeProducerService typeCMergeProducerService) {

    mergeServiceMap = new HashMap<>();

    mergeServiceMap.put(String.valueOf(MergeConstants.MergeType.TYPE_A).toUpperCase(), typeAMergeService);
    mergeServiceMap.put(String.valueOf(MergeConstants.MergeType.TYPE_B).toUpperCase(), typeBMergeService);
    mergeServiceMap.put(String.valueOf(MergeConstants.MergeType.TYPE_C).toUpperCase(), typeCMergeProducerService);

  }

  public AbstractMergeProducerService createMergeProducer(String type) {

    String typeKey = type.toUpperCase();

    return mergeServiceMap.get(typeKey);
  }

  public void registerMergeService(String type, AbstractMergeProducerService mergeService) {
    mergeServiceMap.put(type.toUpperCase(), mergeService);
  }

  public void removeMergeService(String type) {
    mergeServiceMap.remove(type.toUpperCase());
  }

}
