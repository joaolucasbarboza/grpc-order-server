package br.edu.fema.order_server.entity;

import br.edu.fema.grpc.OrderRequest;
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

    private String product_name;

    private int quantity;

    private float unit_price;

    private LocalDateTime order_date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private PaymentEntity payment;

    @Column(name = "tracking_code")
    private String trackingCode;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public OrderEntity converter(OrderRequest request) {
        OrderEntity order = new OrderEntity();
        order.setProduct_name(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setUnit_price(request.getUnitPrice());
        order.setOrder_date(LocalDateTime.now());
        PaymentEntity payment = new PaymentEntity();
        payment.setMethod(request.getPayment().getMethod());

        CreditCardEntity creditCard = new CreditCardEntity();
        creditCard.setCard_brand(request.getPayment().getCreditCard().getCardBrand());
        creditCard.setInstalments(request.getPayment().getCreditCard().getInstalments());

        payment.setCredit_card(creditCard);
        order.setPayment(payment);

        return order;
    }
}
