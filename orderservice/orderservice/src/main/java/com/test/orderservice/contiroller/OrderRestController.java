package com.test.orderservice.contiroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/order")
public class OrderRestController {

    @Value("${server.port}")
    private String port;

    private final RestClient restClient;

    public OrderRestController(RestClient.Builder builder) {
        this.restClient = builder.build();
    }
    @GetMapping("/info")
    public ResponseEntity<String> showOrderInfo() {
        return ResponseEntity.ok("FROM ORDER SERVICE, Port# is: " + port);
    }

    @GetMapping("/getorders")
    public String getOrders() {
        return "Orders from ORDER-SERVICE, Port# is: " + port;
    }
    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable String orderId) {

        String paymentResponse = restClient.get()
                .uri("http://PAYMENT-SERVICE/payment/" + orderId)
                .retrieve()
                .body(String.class);

        return "Order " + orderId + " | " + paymentResponse;
    }
}
