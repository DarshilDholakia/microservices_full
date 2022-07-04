package com.darshil.customer;

import com.darshil.amqp.RabbitMQMessageProducer;
import com.darshil.clients.fraud.FraudCheckResponse;
import com.darshil.clients.fraud.FraudClient;
import com.darshil.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName()) // with Records instead of Classes, the getters for properties are not
                // called 'get[property]()' they are just '[property_name]()'
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email valid
        //todo: check if email not taken

        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://fraud/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        if (fraudCheckResponse.isFraudster()) throw new IllegalStateException("Customer is fraudster so not added");

        // once confirmed that customer is not fraudster, we can publish this customer object to an exchange/queue
        NotificationRequest notificationRequest = new NotificationRequest(customer.getId(), customer.getEmail(), "Hello " + customer.getFirstName() + " " + customer.getLastName() + "!");
        rabbitMQMessageProducer.publish(notificationRequest, "internal.exchange", "internal.notification.routing-key");
    }
}