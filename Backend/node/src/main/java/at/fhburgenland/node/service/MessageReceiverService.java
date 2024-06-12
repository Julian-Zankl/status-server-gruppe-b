package at.fhburgenland.node.service;

import at.fhburgenland.node.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessageReceiverService {
    private final StatusService statusService;
    private final String nodeId = UUID.randomUUID().toString();

    @Autowired
    public MessageReceiverService(StatusService statusService) {
        this.statusService = statusService;
    }

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

    public String getNodeId() {
        return nodeId;
    }
}