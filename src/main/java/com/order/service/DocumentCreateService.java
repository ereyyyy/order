package com.order.service;

import com.order.model.enums.ProductType;

public interface DocumentCreateService {

    void createDocuments(ProductType productType, Long orderId);
}
