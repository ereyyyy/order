package com.order.model.entity;

import com.order.model.enums.DocumentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductEntity {
    private Long id;
    private String name;
    private String producer;
    private Long price;
    private List<DocumentType> documentTypes;
} 