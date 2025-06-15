package com.order.generator;

import com.order.model.enums.DocumentType;

public interface DocumentGenerator {
    void generate(Long orderId);
    DocumentType supports();
}
