package com.order.service.feign;

import com.order.model.entity.ProductEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8085/api")
public interface ProductService {

    @GetMapping(path = "/products")
    List<ProductEntity> getAllProducts();
} 