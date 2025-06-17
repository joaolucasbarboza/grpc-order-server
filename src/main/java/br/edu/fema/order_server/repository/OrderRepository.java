package br.edu.fema.order_server.repository;

import br.edu.fema.order_server.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID>{
}
