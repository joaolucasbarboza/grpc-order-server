package br.edu.fema.order_server.service;

import br.edu.fema.order_server.enums.OrderStatus;
import br.edu.fema.order_server.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ProcessTracking {

    private final OrderRepository orderRepository;

    public void execute(String orderId){
        log.info("START -> Gerando o código de rastreio do ID: {}", orderId);

        try {
            Thread.sleep(60 * 20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        orderRepository.findById(UUID.fromString(orderId)).ifPresent(order -> {
            String trackingCode = generateTrackingCode();

            order.setStatus(OrderStatus.SHIPPED);
            order.setTrackingCode(trackingCode);

            orderRepository.save(order);

            log.info("Código de rastreio {} gerado para o ID: {} com sucesso.", trackingCode, orderId);
        });
    }

    private String generateTrackingCode() {
        String num = String.format("%09d", new Random().nextInt(1_000_000_000));
        String letters = generateRandomLetters();
        return "BR" + num + letters;
    }

    private String generateRandomLetters() {
        StringBuilder letters = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            letters.append((char) ('A' + random.nextInt(26)));
        }
        return letters.toString();
    }
}
