package com.order.generator;

import com.order.model.dto.OrderMessageDto;
import com.order.model.enums.DocumentType;
import com.order.service.rabbit.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class InvoiceGenerator implements DocumentGenerator{

    private final MessageProducer messageProducer;

    public InvoiceGenerator(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void generate(Long orderId) {
        OrderMessageDto orderMessage = new OrderMessageDto(DocumentType.INVOICE.getName(), orderId.toString(), DocumentType.INVOICE.getDocumentContent());
        messageProducer.sendMessage(orderMessage);
    }

    @Override
    public DocumentType supports() {
        return DocumentType.INVOICE;
    }
}
