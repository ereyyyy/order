package com.order.service.impl;

import com.order.generator.DocumentGenerator;
import com.order.generator.DocumentGeneratorFactory;
import com.order.model.entity.ProductEntity;
import com.order.model.enums.DocumentType;
import com.order.registry.SubscriptionRegistry;
import com.order.service.DocumentCreateService;
import com.order.service.feign.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentCreateServiceImpl implements DocumentCreateService {

    private final SubscriptionRegistry registry;
    private final DocumentGeneratorFactory factory;
    private final ProductService productService;

    @Override
    public void createDocuments(String productName, Long orderId) {
        System.out.println("Belge üretimi başlatılıyor - Ürün: " + productName + ", Sipariş ID: " + orderId);
        
        // SubscriptionRegistry'den hangi belgelerin üretileceğini al
        List<DocumentType> docs = registry.getSubscribedDocuments(productName);
        System.out.println("Ürün '" + productName + "' için üretilecek belge sayısı: " + docs.size());
        System.out.println("Üretilecek belgeler: " + docs);
        
        // Product Service'den ürün bilgilerini al
        ProductEntity product = getProductFromService(productName);
        if (product != null) {
            System.out.println("Ürün bilgileri alındı: " + product);
        } else {
            System.out.println("Ürün bilgileri alınamadı, fallback içerik kullanılacak");
        }
        
        for (DocumentType docType : docs) {
            System.out.println("Belge üretiliyor: " + docType.getName());
            DocumentGenerator generator = factory.getGenerator(docType);
            if (generator != null) {
                System.out.println("Generator bulundu: " + generator.getClass().getSimpleName());
                // Ürün bilgilerine göre belge içeriği oluştur
                String dynamicContent = generateDocumentContent(docType, product, productName);
                updateDocumentContent(docType, dynamicContent);
                System.out.println("Belge içeriği güncellendi: " + dynamicContent);
                
                // Generator'ı çağır
                try {
                    generator.generate(orderId);
                    System.out.println("Belge başarıyla üretildi ve gönderildi: " + docType.getName());
                } catch (Exception e) {
                    System.err.println("Belge üretiminde hata: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.err.println("Generator bulunamadı: " + docType.getName());
            }
        }
    }
    
    private ProductEntity getProductFromService(String productName) {
        try {
            System.out.println("ProductService'den ürünler alınıyor...");
            List<ProductEntity> products = productService.getAllProducts();
            System.out.println("ProductService'den " + products.size() + " ürün alındı");
            
            ProductEntity foundProduct = products.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(productName))
                    .findFirst()
                    .orElse(null);
            
            if (foundProduct != null) {
                System.out.println("Ürün bulundu: " + foundProduct.getName());
            } else {
                System.out.println("Ürün bulunamadı: " + productName);
                System.out.println("Mevcut ürünler: " + products.stream().map(ProductEntity::getName).toList());
            }
            
            return foundProduct;
        } catch (Exception e) {
            System.err.println("ProductService'den ürün alınırken hata: " + e.getMessage());
            e.printStackTrace();
            // Product Service erişilemezse null döndür, fallback içerik kullanılacak
            return null;
        }
    }
    
    private String generateDocumentContent(DocumentType docType, ProductEntity product, String productName) {
        if (product != null) {
            String productInfo = String.format("Product: %s, Producer: %s, Price: %d", 
                    product.getName(), product.getProducer(), product.getPrice());
            return String.format("%s content for %s", docType.getName(), productInfo);
        } else {
            return String.format("%s content for %s", docType.getName(), productName);
        }
    }
    
    private void updateDocumentContent(DocumentType docType, String newContent) {
        try {
            Field documentContentField = DocumentType.class.getDeclaredField("documentContent");
            documentContentField.setAccessible(true);
            documentContentField.set(docType, newContent);
            System.out.println("DocumentType içeriği güncellendi: " + docType.getName() + " -> " + newContent);
        } catch (Exception e) {
            System.err.println("DocumentType içeriği güncellenirken hata: " + e.getMessage());
            // Hata durumunda varsayılan içeriği kullan
        }
    }
} 