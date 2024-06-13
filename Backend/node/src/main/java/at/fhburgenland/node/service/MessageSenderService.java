package at.fhburgenland.node.service;

import at.fhburgenland.node.Status;
import at.fhburgenland.node.StatusMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeoutException;

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
        CorrelationData crd = new CorrelationData();
        StatusMessage message = new StatusMessage(messageReceiverService.getNodeId(), operation, status);
        rabbitTemplate.convertAndSend("status_exchange", "", message, crd);
        try {
            CorrelationData.Confirm confirm = crd.getFuture().get(10, TimeUnit.SECONDS);
            if(confirm.isAck()) {
                System.out.println("RabbitMQ acknowledged the message");
            } else {
                System.out.println("RabbitMQ negatively acknowledged the message");
            }
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            System.out.println("RabbitMQ didn't respond to the message");
        }
    }
}