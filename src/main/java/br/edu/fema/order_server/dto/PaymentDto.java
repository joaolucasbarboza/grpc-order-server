package br.edu.fema.order_server.dto;

public record PaymentDto(
        String method,
        CreditCardDto creditCard
) {
}
