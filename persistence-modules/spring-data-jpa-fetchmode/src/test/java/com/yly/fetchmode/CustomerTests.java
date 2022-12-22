package com.yly.fetchmode;

import com.yly.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@SpringBootTest
class CustomerTests {
	@Resource
	private CustomerRepository customerRepository;

	@Test
	void test1() {
		Customer customer = new Customer();

		Order order1 = new Order();
		order1.setName("订单1");
		order1.setCustomer(customer);

		Order order2 = new Order();
		order2.setName("订单2");
		order2.setCustomer(customer);

		Order order3 = new Order();
		order3.setName("订单3");
		order3.setCustomer(customer);

		Order order4 = new Order();
		order4.setName("订单4");
		order4.setCustomer(customer);

		Order order5 = new Order();
		order5.setName("订单5");
		order5.setCustomer(customer);

		Order order6 = new Order();
		order6.setName("订单6");
		order6.setCustomer(customer);

		Order order7 = new Order();
		order7.setName("订单7");
		order7.setCustomer(customer);

		HashSet<Order> set = new HashSet<>();
		set.add(order1);
		set.add(order2);
		set.add(order3);
		set.add(order4);
		set.add(order5);
		set.add(order6);
		set.add(order7);
		customer.setOrders(set);

		customerRepository.save(customer);
	}

	@Test
	@Transactional(rollbackOn = Exception.class)
	void test2() {
		Optional<Customer> byId = customerRepository.findById(26);
		Set<Order> orders = byId.get().getOrders();
		System.out.println(orders.size());
	}

}
