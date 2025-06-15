package com.order.registry;

import com.order.model.enums.DocumentType;
import com.order.model.enums.ProductType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SubscriptionRegistry {

    private final Map<ProductType, List<DocumentType>> subscriptions = new HashMap<>();

    public SubscriptionRegistry() {
        subscriptions.put(ProductType.BORDO_GIYIM_PANTOLON, Arrays.asList(
                DocumentType.INVOICE,
                DocumentType.SHIPPING_LABEL
        ));
        subscriptions.put(ProductType.NIKE_AIR_MAX, Arrays.asList(
                DocumentType.INVOICE,
                DocumentType.WARRANTY_CARD,
                DocumentType.RECEIPT
                ));
        subscriptions.put(ProductType.SAMSUNGA20, List.of(
                DocumentType.INVOICE,
                DocumentType.WARRANTY_CARD,
                DocumentType.RECEIPT
        ));
        subscriptions.put(ProductType.AMAZON_ALEXA, List.of(
                DocumentType.INVOICE,
                DocumentType.WARRANTY_CARD,
                DocumentType.RECEIPT,
                DocumentType.CUSTOMS_DECLARATION
        ));
    }

    public List<DocumentType> getSubscribedDocuments(ProductType productType) {
        return subscriptions.getOrDefault(productType, Collections.emptyList());
    }
}
