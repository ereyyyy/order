package com.order.service.impl;

import com.order.generator.DocumentGenerator;
import com.order.generator.DocumentGeneratorFactory;
import com.order.model.enums.DocumentType;
import com.order.model.enums.ProductType;
import com.order.registry.SubscriptionRegistry;
import com.order.service.DocumentCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentCreateServiceImpl implements DocumentCreateService {

    private final SubscriptionRegistry registry;
    private final DocumentGeneratorFactory factory;

    @Override
    public void createDocuments(ProductType productType, Long orderId) {
        List<DocumentType> docs = registry.getSubscribedDocuments(productType);
        for (DocumentType docType : docs) {
            DocumentGenerator generator = factory.getGenerator(docType);
            if (generator != null) {
                generator.generate(orderId);
            }
        }
    }
}
