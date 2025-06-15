package com.order.generator;

import com.order.model.enums.DocumentType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DocumentGeneratorFactory {
    private final Map<DocumentType, DocumentGenerator> generatorMap = new HashMap<>();

    public DocumentGeneratorFactory(List<DocumentGenerator> generators) {
        for (DocumentGenerator generator : generators) {
            generatorMap.put(generator.supports(), generator);
        }
    }

    public DocumentGenerator getGenerator(DocumentType type) {
        return generatorMap.get(type);
    }
}
