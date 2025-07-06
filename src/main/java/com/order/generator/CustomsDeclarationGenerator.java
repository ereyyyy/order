package com.order.generator;

import com.order.model.dto.OrderMessageDto;
import com.order.model.enums.DocumentType;
import com.order.service.rabbit.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class CustomsDeclarationGenerator implements DocumentGenerator{
    private final MessageProducer messageProducer;

    public CustomsDeclarationGenerator(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void generate(Long orderId) {
        OrderMessageDto orderMessage = new OrderMessageDto(DocumentType.CUSTOMS_DECLARATION.getName(), orderId.toString(), DocumentType.CUSTOMS_DECLARATION.getDocumentContent());
        messageProducer.sendMessage(orderMessage);
    }

    @Override
    public DocumentType supports() {
        return DocumentType.CUSTOMS_DECLARATION;
    }
}
