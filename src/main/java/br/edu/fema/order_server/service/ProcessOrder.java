package br.edu.fema.order_server.service;

import br.edu.fema.order_server.dto.OrderMessageDto;
import br.edu.fema.order_server.entity.OrderEntity;
import br.edu.fema.order_server.enums.OrderStatus;
import br.edu.fema.order_server.message.MessageProducer;
import br.edu.fema.order_server.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class ProcessOrder {

    private final OrderRepository orderRepository;
    private final MessageProducer messageProducer;

    public void execute(String message){
        log.info("START -> Processando mensagem: {}", message);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OrderMessageDto orderMessageDto = objectMapper.readValue(message, OrderMessageDto.class);
            log.info("Mensagem desserializada: {}", orderMessageDto);

            String orderId = processAndSaveNewOrder(orderMessageDto);
            messageProducer.sendMessageTracking(orderId);
        } catch (Exception e) {
            log.error("Erro ao desserializar a mensagem: {}", e.getMessage());
        }
    }

    private String processAndSaveNewOrder(OrderMessageDto orderMessageDto) {
        log.info("Iniciando conversão e salvamento para OrderMessageDto: {}", orderMessageDto);

        OrderEntity newOrder = OrderEntity.from(orderMessageDto);
        BigDecimal total = calculateTotalValue(newOrder.getQuantity(), BigDecimal.valueOf(newOrder.getUnitPrice().floatValue()));

        newOrder.setProductName(orderMessageDto.productName());
        newOrder.setStatus(OrderStatus.AWAITING_SHIPMENT);
        newOrder.setTotalPrice(total);
        orderRepository.save(newOrder);

        log.info("Pedido ID: {} salvo com sucesso.", newOrder.getId());

        return newOrder.getId().toString();
    }

    private BigDecimal calculateTotalValue(int quantity, BigDecimal unityPrice) {
        return unityPrice.multiply(BigDecimal.valueOf(quantity));
    }
}

