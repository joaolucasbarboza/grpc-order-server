package br.edu.fema.order_server.service;

import br.edu.fema.grpc.OrderRequest;
import br.edu.fema.grpc.OrderResponse;
import br.edu.fema.grpc.OrderServiceGrpc;
import br.edu.fema.order_server.entity.OrderEntity;
import br.edu.fema.order_server.message.MessageProducer;
import br.edu.fema.order_server.repository.OrderRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrderRepository orderRepository;
    private final MessageProducer messageProducer;

    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        try {
            OrderEntity orderEntity = new OrderEntity().converter(request);


            orderRepository.save(orderEntity);

            String message = String.format(
                    "Novo pedido criado -> ID: %s, Nome: %s, Quantidade: %d, Preço Unitário: R$ %.2f, Data e Hora do Pedido: %s",
                    orderEntity.getId(),
                    orderEntity.getProduct_name(),
                    orderEntity.getQuantity(),
                    orderEntity.getUnit_price(),
                    orderEntity.getOrder_date()
            );

            messageProducer.sendMessage(message);

            OrderResponse orderResponse = OrderResponse.newBuilder()
                    .setId(orderEntity.getId().toString())
                    .setStatus("PENDING")
                    .setEstimatedDeliveryDate(LocalDate.now().plusDays(7).toString())
                    .setTrackingCode("PENDING")
                    .setCreatedAt(LocalDate.now().toString())
                    .setPayment(request.getPayment())
                    .build();

            responseObserver.onNext(orderResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription("Erro interno: " + e.getMessage())
                            .augmentDescription(e.getLocalizedMessage())
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }
}

