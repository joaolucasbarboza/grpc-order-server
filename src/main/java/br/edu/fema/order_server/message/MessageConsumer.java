package br.edu.fema.order_server.message;

import br.edu.fema.order_server.configuration.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listen(String message) {
        System.out.println("Mensagem recebida: " + message);
    }
}
