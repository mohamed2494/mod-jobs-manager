package org.folio.factory;

import org.folio.service.merge.AbstractMergeProducerService;
import org.folio.service.merge.TypeAMergeProducerService;
import org.folio.service.merge.TypeBMergeProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MergeProducerFactory {
    private final TypeAMergeProducerService typeAMergeService;
    private final TypeBMergeProducerService typeBMergeService;

    @Autowired
    public MergeProducerFactory(TypeAMergeProducerService typeAMergeService, TypeBMergeProducerService typeBMergeService) {
        this.typeAMergeService = typeAMergeService;
        this.typeBMergeService = typeBMergeService;
    }

    public AbstractMergeProducerService createMergeProducer(String type) {

        if ("TypeA".equalsIgnoreCase(type)) {
            return typeAMergeService;
        } else if ("TypeB".equalsIgnoreCase(type)) {
            return typeBMergeService;
        }
        // Handle unknown merge types or return a default producer
        return typeAMergeService;
    }
}
