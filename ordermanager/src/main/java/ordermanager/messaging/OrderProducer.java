package ordermanager.messaging;

import ordermanager.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderCreatedMessage(Long orderId) {

        String message =
                "Pedido #" + orderId + " enviado para processamento";

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_CREATED_QUEUE,
                message
        );
    }
}