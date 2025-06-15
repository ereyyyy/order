package com.order.generator;

import com.order.model.dto.OrderMessageDto;
import com.order.model.enums.DocumentType;
import com.order.service.rabbit.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class ShippingLabelGenerator implements DocumentGenerator{

    private final MessageProducer messageProducer;

    public ShippingLabelGenerator(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @Override
    public void generate(Long orderId) {
        OrderMessageDto orderMessage = new OrderMessageDto(DocumentType.SHIPPING_LABEL.getName(), orderId.toString(), DocumentType.SHIPPING_LABEL.getDocumentContent());
        messageProducer.sendMessage(orderMessage);
    }

    @Override
    public DocumentType supports() {
        return DocumentType.SHIPPING_LABEL;
    }
}
