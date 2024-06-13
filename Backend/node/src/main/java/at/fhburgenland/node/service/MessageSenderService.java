package at.fhburgenland.node.service;

import at.fhburgenland.node.Status;
import at.fhburgenland.node.StatusMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service for sending messages to the message broker
 */
@Component
public class MessageSenderService {
    private final RabbitTemplate rabbitTemplate;
    private final MessageReceiverService messageReceiverService;

    /**
     * Constructor
     * @param rabbitTemplate RabbitTemplate for sending messages
     * @param messageReceiverService MessageReceiverService for getting the node id
     */
    @Autowired
    public MessageSenderService(RabbitTemplate rabbitTemplate, MessageReceiverService messageReceiverService) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageReceiverService = messageReceiverService;
    }

    /**
     * Send a message to the message broker
     * @param operation Operation to be executed
     * @param status Status to be sent
     */
    public void sendMessage(String operation, Status status) {
        StatusMessage message = new StatusMessage(messageReceiverService.getNodeId(), operation, status);
        rabbitTemplate.convertAndSend("status_exchange", "", message);
    }
}