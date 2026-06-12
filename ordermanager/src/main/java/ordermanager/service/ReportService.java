package ordermanager.service;

import ordermanager.enums.OrderStatus;
import ordermanager.repository.OrderRepository;
import ordermanager.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ReportService(
            JdbcTemplate jdbcTemplate,
            UserRepository userRepository,
            OrderRepository orderRepository
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public Number getTotalOrders() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("TOTAL_PEDIDOS");

        Map<String, Object> result = jdbcCall.execute();

        return (Number) result.get("P_TOTAL");
    }

    public Map<String, Object> getSummary() {
        var orders = orderRepository.findAll();

        long created = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CREATED)
                .count();

        long processing = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PROCESSING)
                .count();

        long completed = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                .count();

        long canceled = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CANCELED)
                .count();

        return Map.of(
                "totalUsers", userRepository.count(),
                "totalOrders", orders.size(),
                "createdOrders", created,
                "processingOrders", processing,
                "completedOrders", completed,
                "canceledOrders", canceled,
                "totalOrdersProcedure", getTotalOrders()
        );
    }
}