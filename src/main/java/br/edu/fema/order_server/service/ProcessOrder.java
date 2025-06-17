package br.edu.fema.order_server.service;

import org.springframework.stereotype.Service;

@Service
public class ProcessOrder {

    public void execute(String message) {
        System.out.println("Processando pedido: " + message);
    }
}
