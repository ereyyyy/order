package com.order.generator;

import com.order.model.dto.OrderMessageDto;
import com.order.model.enums.DocumentType;
import com.order.service.rabbit.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class ReceiptGenerator implements DocumentGenerator{
    private final MessageProducer messageProducer;

    public ReceiptGenerator(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void generate(Long orderId) {
        OrderMessageDto orderMessage = new OrderMessageDto(DocumentType.RECEIPT.getName(), orderId.toString(), DocumentType.RECEIPT.getDocumentContent());
        messageProducer.sendMessage(orderMessage);
    }

    @Override
    public DocumentType supports() {
        return DocumentType.RECEIPT;
    }

}
