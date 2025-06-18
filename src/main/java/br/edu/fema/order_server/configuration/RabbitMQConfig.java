package br.edu.fema.order_server.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE = "orderQueue";
    public static final String ORDER_EXCHANGE = "orderExchange";
    public static final String ORDER_ROUTING_KEY = "orderRoutingKey";

    public static final String TRACKING_QUEUE = "trackingQueue";
    public static final String TRACKING_ROUTING_KEY = "trackingRoutingKey";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(ORDER_QUEUE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDER_ROUTING_KEY);
    }

    @Bean
    public Queue trackingQueue() {
        return new Queue(TRACKING_QUEUE, true);
    }

    @Bean
    public Binding trackingBinding(Queue trackingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(trackingQueue).to(exchange).with(TRACKING_ROUTING_KEY);
    }
}
