package com.example.controllers;

import com.example.model.persistence.User;
import com.example.model.persistence.UserOrder;
import com.example.model.persistence.repositories.OrderRepository;
import com.example.model.persistence.repositories.UserRepository;
import com.example.demo.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest extends CreationHelper {

    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
    }

    @Test
    public void validateSubmitOrder() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        final ResponseEntity<UserOrder> response = orderController.submit("Adrian");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder responseBodyOrder = response.getBody();
        assertNotNull(responseBodyOrder);
        assertEquals("Adrian", responseBodyOrder.getUser().getUsername());
    }

    @Test
    public void validateSubmitOrderNegativeScenario() {
        final ResponseEntity<UserOrder> response = orderController.submit("Adrian");
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void validateGetOrdersForUser() {
        User user = createUser();
        when(userRepository.findByUsername("Adrian")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(createUserOrders());
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Adrian");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<UserOrder> responseBodyOrders = response.getBody();
        assertNotNull(responseBodyOrders);
        assertEquals(new BigDecimal(75), responseBodyOrders.get(1).getTotal());
    }

    @Test
    public void validateGetOrdersForUserNegativeScenario() {
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("Adrian");
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
