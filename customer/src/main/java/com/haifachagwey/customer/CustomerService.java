package com.haifachagwey.customer;

import com.haifachagwey.amqp.RabbitMQMessageProducer;
import com.haifachagwey.clients.fraud.FraudCheckResponse;
import com.haifachagwey.clients.fraud.FraudClient;
import com.haifachagwey.clients.notification.NotificationClient;
import com.haifachagwey.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
//    private final RestTemplate restTemplate;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

//    public CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient, RestTemplate restTemplate) {
//        this.customerRepository = customerRepository;
//        this.fraudClient = fraudClient;
//        this.notificationClient = notificationClient;
//        this.restTemplate = restTemplate;
//    }
    public CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient, RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.customerRepository = customerRepository;
        this.fraudClient = fraudClient;
        this.notificationClient = notificationClient;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email is valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);
// **************** Rest Template ****************
//        com.haifachagwey.clients.fraud.FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
//                com.haifachagwey.clients.fraud.FraudCheckResponse.class,
//                customer.getId());
//        com.haifachagwey.clients.fraud.FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
//                com.haifachagwey.clients.fraud.FraudCheckResponse.class,
//                customer.getId());

// **************** Open Feign ****************
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
//        Before using RabbitMQ
//        notificationClient.sendNotification(
//                new NotificationRequest(
//                        customer.getId(),
//                        customer.getEmail(),
//                        String.format("Hi %s, welcome to Amigoscode", customer.getFirstName())
//                )
//        );
//        After using RabbitMQ
        NotificationRequest notificationRequest =  new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Amigoscode", customer.getFirstName())
                );
        rabbitMQMessageProducer.publish(
                notificationRequest,
//                TODO: Refactor
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
