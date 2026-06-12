package ordermanager.controller;

import ordermanager.dto.OrderCreateRequest;
import ordermanager.dto.OrderResponse;
import ordermanager.enums.OrderStatus;
import ordermanager.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse criarPedido(@RequestBody OrderCreateRequest request) {
        return orderService.criarPedido(request);
    }

    @GetMapping
    public List<OrderResponse> listarPedidos() {
        return orderService.listarPedidos();
    }

    @PatchMapping("/{id}/status")
    public OrderResponse atualizarStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        OrderStatus status = OrderStatus.valueOf(body.get("status"));
        return orderService.atualizarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}