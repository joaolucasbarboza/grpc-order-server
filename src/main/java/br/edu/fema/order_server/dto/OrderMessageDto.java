package br.edu.fema.order_server.dto;

import java.math.BigDecimal;

public record OrderMessageDto(
        String productName,
        int quantity,
        BigDecimal unitPrice,
        PaymentDto payment
) {
}
