package br.edu.fema.order_server.service;

import br.edu.fema.grpc.OrderRequest;
import br.edu.fema.grpc.OrderResponse;
import br.edu.fema.grpc.OrderServiceGrpc;
import br.edu.fema.order_server.enums.OrderStatus;
import br.edu.fema.order_server.message.MessageProducer;
import br.edu.fema.order_server.service.stepsProcess.generatedJsonMessage;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final MessageProducer messageProducer;
    private final generatedJsonMessage generatedJsonMessage;

    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        try {

            String jsonMessage = generatedJsonMessage.generateJsonMessage(request);
            messageProducer.sendMessageOrder(jsonMessage);

            OrderResponse orderResponse = OrderResponse.newBuilder()
                    .setProductName(request.getProductName())
                    .setStatus(OrderStatus.PENDING.toString())
                    .setEstimatedDeliveryDate(LocalDate.now().plusDays(7).toString())
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

