package com.order.registry;

import com.order.model.entity.ProductEntity;
import com.order.model.enums.DocumentType;
import com.order.service.feign.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionRegistry {

    private final ProductService productService;
    private final Map<String, List<DocumentType>> subscriptions = new HashMap<>();

    public List<DocumentType> getSubscribedDocuments(String productName) {
        // Eğer cache'de yoksa Product Service'den al
        if (subscriptions.isEmpty()) {
            loadProductsFromService();
        }
        
        return subscriptions.getOrDefault(productName, Collections.emptyList());
    }
    
    private void loadProductsFromService() {
        try {
            List<ProductEntity> products = productService.getAllProducts();
            subscriptions.clear();
            
            for (ProductEntity product : products) {
                // Product Service'den gelen belge türlerini Order Service'deki enum'lara dönüştür
                List<DocumentType> convertedDocumentTypes = convertDocumentTypes(product.getDocumentTypes());
                subscriptions.put(product.getName(), convertedDocumentTypes);
            }
        } catch (Exception e) {
            // Product Service erişilemezse fallback mapping kullan
            loadFallbackSubscriptions();
        }
    }
    
    private List<DocumentType> convertDocumentTypes(List<?> productDocumentTypes) {
        List<DocumentType> convertedTypes = new ArrayList<>();
        
        for (Object productDocType : productDocumentTypes) {
            try {
                // String ismine göre Order Service'deki enum'u bul (case-insensitive)
                String docTypeName = productDocType.toString().toUpperCase();
                DocumentType orderDocType = DocumentType.valueOf(docTypeName);
                convertedTypes.add(orderDocType);
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown document type: " + productDocType.toString());
            }
        }
        
        return convertedTypes;
    }
    
    private void loadFallbackSubscriptions() {
        // Eski statik mapping'i fallback olarak kullan
        subscriptions.put("BORDO_GIYIM_PANTOLON", Arrays.asList(
                DocumentType.INVOICE,
                DocumentType.SHIPPING_LABEL
        ));
        subscriptions.put("NIKE_AIR_MAX", Arrays.asList(
                DocumentType.INVOICE,
                DocumentType.WARRANTY_CARD,
                DocumentType.RECEIPT
        ));
        subscriptions.put("SAMSUNGA20", List.of(
                DocumentType.INVOICE,
                DocumentType.WARRANTY_CARD,
                DocumentType.RECEIPT
        ));
        subscriptions.put("AMAZON_ALEXA", List.of(
                DocumentType.INVOICE,
                DocumentType.WARRANTY_CARD,
                DocumentType.RECEIPT,
                DocumentType.CUSTOMS_DECLARATION
        ));
    }
}
