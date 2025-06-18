package br.edu.fema.order_server.message;

import br.edu.fema.order_server.configuration.RabbitMQConfig;
import br.edu.fema.order_server.service.ProcessOrder;
import br.edu.fema.order_server.service.ProcessTracking;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MessageConsumer {

    private final ProcessOrder processOrder;
    private final ProcessTracking processTracking;
    private final static String ORDER_QUEUE = "ORDER_QUEUE";
    private final static String TRACKING_QUEUE = "TRACKING_QUEUE";

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void listenOrder(String message) {
        generatedLog(ORDER_QUEUE, message);

        processOrder.execute(message);
    }

    @RabbitListener(queues = RabbitMQConfig.TRACKING_QUEUE)
    public void listenTracking(String message) {
       generatedLog(TRACKING_QUEUE, message);

       processTracking.execute(message);
    }

    private static void generatedLog(String queue, String message) {
        log.info("Consumindo mensagem da fila: {}: {}", queue, message);
    }
}
