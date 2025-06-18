package br.edu.fema.order_server.service.stepsProcess;

import br.edu.fema.grpc.OrderRequest;
import br.edu.fema.order_server.dto.CreditCardDto;
import br.edu.fema.order_server.dto.OrderMessageDto;
import br.edu.fema.order_server.dto.PaymentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class generatedJsonMessage {

    public String generateJsonMessage(OrderRequest request) throws JsonProcessingException {

        CreditCardDto creditCardDto = null;
        if (request.getPayment().hasCreditCard()) {
            creditCardDto = new CreditCardDto(
                    request.getPayment().getCreditCard().getInstalments(),
                    request.getPayment().getCreditCard().getCardBrand()
            );
        }

        PaymentDto paymentDto = new PaymentDto(
                request.getPayment().getMethod(),
                creditCardDto
        );

        OrderMessageDto message = new OrderMessageDto(
                request.getProductName(),
                request.getQuantity(),
                BigDecimal.valueOf(request.getUnitPrice()),
                paymentDto
        );

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(message);
    }
}
