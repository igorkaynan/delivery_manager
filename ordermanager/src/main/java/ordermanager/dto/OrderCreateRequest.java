package ordermanager.dto;

public class OrderCreateRequest {

    private String description;
    private Double totalValue;

    public String getDescription() {
        return description;
    }

    public Double getTotalValue() {
        return totalValue;
    }
}