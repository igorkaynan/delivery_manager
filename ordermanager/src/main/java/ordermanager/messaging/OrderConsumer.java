package ordermanager.messaging;

import ordermanager.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void receiveOrderCreatedMessage(String message) {
        System.out.println("Mensagem recebida do RabbitMQ: " + message);
    }
}