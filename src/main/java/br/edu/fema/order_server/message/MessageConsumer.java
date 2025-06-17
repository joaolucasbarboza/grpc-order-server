package br.edu.fema.order_server.message;

import br.edu.fema.order_server.configuration.RabbitMQConfig;
import br.edu.fema.order_server.service.ProcessOrder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MessageConsumer {

    private final ProcessOrder processOrder;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void listen(String message) {
        log.info("Consumindo mensagem: {}", message);

        processOrder.execute(message);
    }
}
