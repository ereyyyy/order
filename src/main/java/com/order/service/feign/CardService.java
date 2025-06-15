package com.order.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "card-service", url = "http://localhost:8083/api")
public interface CardService {

    @PostMapping(path = "/place-order")
    List<String> placeOrder(@RequestBody String user);

    @PostMapping(path = "/get-order-id")
    Long getOrderId(@RequestBody String user);
}
