package at.fhburgenland.node.service;

import at.fhburgenland.node.StatusMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Service for receiving messages from other instances
 */
@Component
public class MessageReceiverService implements ChannelAwareMessageListener {
    private final StatusService statusService;
    private final String nodeId = UUID.randomUUID().toString();
    private final MessageConverter converter;

    /**
     * Constructor
     * @param statusService StatusService for status operations
     */
    @Autowired
    public MessageReceiverService(StatusService statusService, MessageConverter converter) {
        this.statusService = statusService;
        this.converter = converter;
    }

    /**
     * Receive message from other instances
     * @param message Message to be received
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("received message");

        if (channel != null) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println(message.getMessageProperties().getDeliveryTag());
        }

        StatusMessage received = (StatusMessage) converter.fromMessage(message);
        String instanceIdHeader = received.getNodeId();

        if (!nodeId.equals(instanceIdHeader)) {
            switch (received.getOperation()) {
                case "CREATE" -> statusService.createStatus(received.getStatus());
                case "UPDATE" -> statusService.updateStatus(received.getStatus(), received.getStatus().getUsername());
                case "DELETE" -> statusService.deleteStatus(received.getStatus().getUsername());
            }
        }
    }

    /**
     * Get node id
     * @return Node id
     */
    public String getNodeId() {
        return nodeId;
    }
}