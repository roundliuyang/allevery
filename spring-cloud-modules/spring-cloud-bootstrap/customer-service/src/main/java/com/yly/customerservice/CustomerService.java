package com.yly.customerservice;


import com.yly.orderservice.client.OrderClient;
import com.yly.orderservice.client.OrderDTO;
import com.yly.orderservice.client.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerService {

    @Autowired
    private OrderClient orderClient;

    private List<Customer> customers = Arrays.asList(

            new Customer(1, "John", "Smith"),
            new Customer(2, "Deny", "Dominic"));


    @GetMapping
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


    @PostMapping(value = "/order")
    public String sendOrder(@RequestBody Map<String, Object> body) {

        OrderDTO dto = new OrderDTO();
        dto.setCustomerId((Integer) body.get("customerId"));
        dto.setItemId((String) body.get("itemId"));

        OrderResponse response = orderClient.order(dto);

        return response.getStatus();
    }

}