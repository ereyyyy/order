package com.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderMessageDto {
    private String documentType;
    private String orderId;
    private String documentContent;
}
