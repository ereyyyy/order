package com.order.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DocumentType {
    INVOICE("INVOICE", "Document Content for invoice"),// Fatura
    SHIPPING_LABEL("SHIPPING_LABEL", "Document Content for shipping label"),// Kargo etiketi
    WARRANTY_CARD("WARRANTY_CARD", "Document Content for warranty card"),// Garanti belgesi
    RECEIPT("RECEIPT", "Document Content for receipt"),// Ödeme makbuzu
    CUSTOMS_DECLARATION("CUSTOMS_DECLARATION", "Document Content for customs declaration");// Gümrük beyannamesi (yurt dışı)

    private final String name;
    private final String documentContent;
}
