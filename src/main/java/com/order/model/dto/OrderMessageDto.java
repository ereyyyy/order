package com.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class OrderMessageDto implements Serializable {
    private String documentType;
    private String orderId;
    private String documentContent;
}
