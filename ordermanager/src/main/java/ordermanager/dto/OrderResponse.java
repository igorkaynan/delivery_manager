package ordermanager.dto;

import ordermanager.enums.OrderStatus;

import java.time.LocalDateTime;

public class OrderResponse {

    private Long id;
    private String description;
    private Double totalValue;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public OrderResponse(Long id, String description, Double totalValue, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.totalValue = totalValue;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}