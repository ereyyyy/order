package com.order.service.impl;

import com.order.service.OrderService;
import com.order.service.feign.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final DocumentCreateServiceImpl documentCreateService;
    private final CardService cardService;

    @Override
    public void placeOrder(String userName) {
        Long orderId = cardService.getOrderId(userName);
        List<String> products = cardService.placeOrder(userName);
        for (String product : products) {
            documentCreateService.createDocuments(product, orderId);
        }
    }
}
