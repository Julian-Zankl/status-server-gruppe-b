package at.fhburgenland.backend.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue createQueue() {
        return new Queue("createQueue", true);
    }

    @Bean
    public Queue readQueue() {
        return new Queue("readQueue", true);
    }

    @Bean
    public Queue updateQueue() {
        return new Queue("updateQueue", true);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue("deleteQueue", true);
    }
}
