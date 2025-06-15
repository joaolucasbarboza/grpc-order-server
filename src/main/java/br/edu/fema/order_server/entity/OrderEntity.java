package br.edu.fema.order_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String product;

    private int quantity;

    public OrderEntity(String product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
