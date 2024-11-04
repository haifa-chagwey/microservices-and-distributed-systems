package com.haifachagwey.customer;

import com.haifachagwey.clients.fraud.FraudCheckResponse;
import com.haifachagwey.clients.fraud.FraudClient;
import com.haifachagwey.clients.notification.NotificationClient;
import com.haifachagwey.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient) {
        this.customerRepository = customerRepository;
        this.fraudClient = fraudClient;
        this.notificationClient = notificationClient;
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

//        com.haifachagwey.clients.fraud.FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
//                com.haifachagwey.clients.fraud.FraudCheckResponse.class,
//                customer.getId());
//        com.haifachagwey.clients.fraud.FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
//                com.haifachagwey.clients.fraud.FraudCheckResponse.class,
//                customer.getId());
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }
        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Amigoscode", customer.getFirstName())
                )
        );
    }
}
