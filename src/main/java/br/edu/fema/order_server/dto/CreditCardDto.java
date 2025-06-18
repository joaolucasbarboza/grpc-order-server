package br.edu.fema.order_server.dto;

public record CreditCardDto(
        int instalments,
        String cardBrand
) {
}
