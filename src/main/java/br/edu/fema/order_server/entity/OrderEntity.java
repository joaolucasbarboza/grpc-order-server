package br.edu.fema.order_server.entity;

import br.edu.fema.order_server.dto.CreditCardDto;
import br.edu.fema.order_server.dto.OrderMessageDto;
import br.edu.fema.order_server.dto.PaymentDto;
import br.edu.fema.order_server.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name")
    private String productName;

    private int quantity;

    private OrderStatus status;

    private BigDecimal unitPrice;

    private LocalDateTime order_date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @Column(name = "tracking_code")
    private String trackingCode;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public static OrderEntity from(OrderMessageDto messageDto) {
        OrderEntity order = new OrderEntity();
        order.setProductName(messageDto.productName());
        order.setQuantity(messageDto.quantity());
        order.setUnitPrice(BigDecimal.valueOf(messageDto.unitPrice().floatValue()));
        order.setOrder_date(LocalDateTime.now());

        PaymentDto paymentDto = messageDto.payment();
        if (paymentDto != null) {
            PaymentEntity paymentEntity = getPaymentEntity(paymentDto);
            order.setPayment(paymentEntity);
        }
        return order;
    }

    private static PaymentEntity getPaymentEntity(PaymentDto paymentDto) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setMethod(paymentDto.method());

        CreditCardDto creditCardDto = paymentDto.creditCard();
        if (creditCardDto != null) {
            CreditCardEntity creditCardEntity = new CreditCardEntity();
            creditCardEntity.setCard_brand(creditCardDto.cardBrand());
            creditCardEntity.setInstalments(creditCardDto.instalments());
            paymentEntity.setCredit_card(creditCardEntity);
        }
        return paymentEntity;
    }
}
