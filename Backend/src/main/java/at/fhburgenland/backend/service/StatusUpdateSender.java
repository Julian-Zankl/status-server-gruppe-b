package at.fhburgenland.backend.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.fhburgenland.backend.Status;

@Service
public class StatusUpdateSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendCreateStatusUpdate(Status status) {
        rabbitTemplate.convertAndSend("createQueue", status);
    }

    public void sendReadStatusUpdate(Status status) {
        rabbitTemplate.convertAndSend("readQueue", status);
    }

    public void sendUpdateStatusUpdate(Status status) {
        rabbitTemplate.convertAndSend("updateQueue", status);
    }

    public void sendDeleteStatusUpdate(Status status) {
        rabbitTemplate.convertAndSend("deleteQueue", status);
    }
}
