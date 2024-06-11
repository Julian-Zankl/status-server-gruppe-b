package at.fhburgenland.backend.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import at.fhburgenland.backend.Status;

@Service
public class StatusUpdateReceiver {

    @RabbitListener(queues = "createQueue")
    public void receiveCreateStatusUpdate(Status status) {
        System.out.println("Received Create Status Update: " + status);
        // Verarbeitung der Create-Status-Updates
    }

    @RabbitListener(queues = "readQueue")
    public void receiveReadStatusUpdate(Status status) {
        System.out.println("Received Read Status Update: " + status);
        // Verarbeitung der Read-Status-Updates
    }

    @RabbitListener(queues = "updateQueue")
    public void receiveUpdateStatusUpdate(Status status) {
        System.out.println("Received Update Status Update: " + status);
        // Verarbeitung der Update-Status-Updates
    }

    @RabbitListener(queues = "deleteQueue")
    public void receiveDeleteStatusUpdate(Status status) {
        System.out.println("Received Delete Status Update: " + status);
        // Verarbeitung der Delete-Status-Updates
    }
}
