package at.fhburgenland.node.service;

import at.fhburgenland.node.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Service for receiving messages from other instances
 */
@Component
public class MessageReceiverService {
    private final StatusService statusService;
    private final String nodeId = UUID.randomUUID().toString();

    /**
     * Constructor
     * @param statusService StatusService for status operations
     */
    @Autowired
    public MessageReceiverService(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * Receive message from other instances
     * @param message Message to be received
     */
    public void receiveMessage(StatusMessage message) {
        System.out.println("received message");
        String instanceIdHeader = message.getNodeId();
        if (!nodeId.equals(instanceIdHeader)) {
            switch (message.getOperation()) {
                case "CREATE" -> statusService.createStatus(message.getStatus());
                case "UPDATE" -> statusService.updateStatus(message.getStatus(), message.getStatus().getUsername());
                case "DELETE" -> statusService.deleteStatus(message.getStatus().getUsername());
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