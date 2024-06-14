package at.fhburgenland.node;

import at.fhburgenland.node.service.MessageReceiverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Configuration for RabbitMQ.
 */
@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.route}")
    private String url;

    /**
     * Create a fanout exchange.
     * @return Fanout exchange
     */
    @Bean
    public FanoutExchange createFanoutExchange() {
        return new FanoutExchange("status_exchange");
    }

    /**
     * Create a queue.
     * @return Queue
     */
    @Bean
    public Queue createNodeQueue() {
        return new Queue("", false, false, true);
    }

    /**
     * Create a binding between the fanout exchange and the queue.
     * @param fanoutExchange Fanout exchange
     * @param nodeQueue Queue
     * @return Binding
     */
    @Bean
    public Binding binding(FanoutExchange fanoutExchange, Queue nodeQueue) {
        return BindingBuilder.bind(nodeQueue).to(fanoutExchange);
    }

    /**
     * Create a connection factory.
     * @return Connection factory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(url);
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("pw");
        connectionFactory.setPublisherReturns(true);
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    /**
     * Create a message listener container.
     * @param connectionFactory Connection factory
     * @param receiverService Receiver service
     * @return Message listener container
     */
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageReceiverService receiverService) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueues(createNodeQueue());
        container.setMessageListener(receiverService);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }

    /**
     * Create a message converter.
     * @return Message converter
     */
    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(mapper);
    }

    /**
     * Create a rabbit template.
     * @param connectionFactory Connection factory
     * @param messageConverter Message converter
     * @return Rabbit template
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setMandatory(true);

        return template;
    }
}