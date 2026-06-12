package ordermanager.service;

import ordermanager.dto.OrderCreateRequest;
import ordermanager.dto.OrderResponse;
import ordermanager.entity.Order;
import ordermanager.enums.OrderStatus;
import ordermanager.messaging.OrderProducer;
import ordermanager.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final AuditLogService auditLogService;

    public OrderService(
            OrderRepository orderRepository,
            OrderProducer orderProducer,
            AuditLogService auditLogService
    ) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
        this.auditLogService = auditLogService;
    }

    public OrderResponse criarPedido(OrderCreateRequest request) {

        Order order = new Order();

        order.setDescription(request.getDescription());
        order.setTotalValue(request.getTotalValue());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        orderProducer.sendOrderCreatedMessage(savedOrder.getId());

        auditLogService.saveLog(
                "ORDER_CREATED",
                "Pedido #" + savedOrder.getId() + " criado e enviado para fila"
        );

        return toResponse(savedOrder);
    }

    public List<OrderResponse> listarPedidos() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse atualizarStatus(Long id, OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        auditLogService.saveLog(
                "ORDER_STATUS_CHANGED",
                "Pedido #" + updatedOrder.getId() + " alterado para " + updatedOrder.getStatus()
        );

        return toResponse(updatedOrder);
    }


    public void deleteOrder(Long id) {

        if (!orderRepository.existsById(id)) {
            return;
        }

        orderRepository.deleteById(id);

        auditLogService.saveLog(
                "ORDER_DELETED",
                "Pedido #" + id + " removido"
        );
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getDescription(),
                order.getTotalValue(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}