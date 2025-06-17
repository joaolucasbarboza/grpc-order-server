package br.edu.fema.order_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "credit_card")
@Getter
@Setter
@NoArgsConstructor
public class CreditCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer instalments;

    private String card_brand;
}
