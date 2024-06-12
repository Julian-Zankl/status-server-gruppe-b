package at.fhburgenland.node.service;

import at.fhburgenland.node.Status;
import at.fhburgenland.node.StatusMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSenderService {
    private final RabbitTemplate rabbitTemplate;
    private final MessageReceiverService messageReceiverService;

    @Autowired
    public MessageSenderService(RabbitTemplate rabbitTemplate, MessageReceiverService messageReceiverService) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageReceiverService = messageReceiverService;
    }

    public void sendMessage(String operation, Status status) {
        StatusMessage message = new StatusMessage(messageReceiverService.getNodeId(), operation, status);
        rabbitTemplate.convertAndSend("status_exchange", "", message);
    }
}