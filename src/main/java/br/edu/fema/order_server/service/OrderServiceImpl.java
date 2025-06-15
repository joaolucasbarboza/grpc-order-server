package br.edu.fema.order_server.service;

import br.edu.fema.grpc.OrderRequest;
import br.edu.fema.grpc.OrderResponse;
import br.edu.fema.grpc.OrderServiceGrpc;
import br.edu.fema.order_server.entity.OrderEntity;
import br.edu.fema.order_server.repository.OrderRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrderRepository orderRepository;

    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {

        OrderEntity order = new OrderEntity(
                request.getProduct(),
                request.getQuantity()
        );

        orderRepository.save(order);
        OrderEntity orderEntity = orderRepository.findById(order.getId()).get();

        OrderResponse orderResponse = OrderResponse
                .newBuilder()
                .setId(orderEntity.getId().toString())
                .setProduct(orderEntity.getProduct())
                .setQuantity(orderEntity.getQuantity())
                .build();

        responseObserver.onNext(orderResponse);
        responseObserver.onCompleted();
    }
}
