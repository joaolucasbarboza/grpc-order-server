package br.edu.fema.order_server.service;

import br.edu.fema.order_server.entity.OrderEntity;
import br.edu.fema.order_server.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProcessOrder {

    private final OrderRepository orderRepository;

    public void execute(String message){
        System.out.println("Processando mensagem: " + message);

        String id = extractIdOfMessage(message);

        Optional<OrderEntity> orderOptional = orderRepository.findById(UUID.fromString(id));

        if(orderOptional.isPresent()){
            OrderEntity order = orderOptional.get();

            String trackingCode = generateTrackingCode();
            BigDecimal total = calculateTotalValue(order.getQuantity(), BigDecimal.valueOf(order.getUnit_price()));

            order.setTrackingCode(trackingCode);
            order.setTotalPrice(total);

            orderRepository.save(order);

            System.out.println("Pedido atualizado com sucesso! -> " + order.getId());
        }
    }

    private String generateTrackingCode() {
        String numbers = String.format("%09d", new Random().nextInt(1_000_000_000));
        String letters = generateRandomLetters();
        return "BR" + numbers + letters;
    }

    private String generateRandomLetters() {
        StringBuilder letters = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            letters.append((char) ('A' + random.nextInt(26)));
        }
        return letters.toString();
    }

    private BigDecimal calculateTotalValue(int quantity, BigDecimal unityPrice) {
        return unityPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private String extractIdOfMessage(String message) {
        int idIndex = message.indexOf("ID: ") + 4;
        int commaIndex = message.indexOf(",", idIndex);
        return message.substring(idIndex, commaIndex).trim();
    }
}

