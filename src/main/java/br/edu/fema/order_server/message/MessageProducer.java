package br.edu.fema.order_server.message;

import br.edu.fema.order_server.configuration.RabbitMQConfig;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageOrder(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ORDER_ROUTING_KEY,
                message
        );
    }

    public void sendMessageTracking(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.TRACKING_ROUTING_KEY,
                message
        );
    }
}
