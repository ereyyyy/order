package com.order.generator;

import com.order.model.dto.OrderMessageDto;
import com.order.model.enums.DocumentType;
import com.order.service.rabbit.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class WarrantyCardGenerator implements DocumentGenerator{
    private final MessageProducer messageProducer;

    public WarrantyCardGenerator(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void generate(Long orderId) {
        OrderMessageDto orderMessage = new OrderMessageDto(DocumentType.WARRANTY_CARD.getName(), orderId.toString(), DocumentType.WARRANTY_CARD.getDocumentContent());
        messageProducer.sendMessage(orderMessage);
    }

    @Override
    public DocumentType supports() {
        return DocumentType.WARRANTY_CARD;
    }
}
