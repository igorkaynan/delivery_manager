package ordermanager.controller;

import ordermanager.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/total-orders")
    public Map<String, Number> getTotalOrders() {
        return Map.of(
                "totalOrders",
                reportService.getTotalOrders()
        );
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return reportService.getSummary();
    }
}