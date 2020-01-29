package com.example.controllers;

import com.example.model.persistence.Cart;
import com.example.model.persistence.repositories.CartRepository;
import com.example.model.persistence.repositories.ItemRepository;
import com.example.model.persistence.repositories.UserRepository;
import com.example.demo.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest extends CreationHelper {
    private CartController cartController;
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
    }

    @Test
    public void validateAddToCart() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        ResponseEntity<Cart> response = cartController.addToCart(createModifyCartRequest());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart responseBodyCart = response.getBody();
        assertNotNull(responseBodyCart);
        assertEquals("Adrian", responseBodyCart.getUser().getUsername());
    }

    @Test
    public void validateAddToCartNegativeScenarioWrongName() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        ResponseEntity<Cart> response = cartController.addToCart
                (createModifyCartRequestNegativeScenarioWrongName());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void validateAddToCartNegativeScenarioWrongItemNumber() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        ResponseEntity<Cart> response = cartController.addToCart
                (createModifyCartRequestNegativeScenarioWrongItem());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void validateRemoveFromCart() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        ResponseEntity<Cart> response = cartController.removeFromCart(createModifyCartRequest());
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Cart responseBodyCart = response.getBody();
        assertNotNull(responseBodyCart);
        assertEquals("Adrian", responseBodyCart.getUser().getUsername());
    }

    @Test
    public void validateRemoveFromCartNegativeScenarioWrongName() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        ResponseEntity<Cart> response = cartController.removeFromCart
                (createModifyCartRequestNegativeScenarioWrongName());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void validateRemoveFromCartNegativeScenarioWrongItem() {
        when(userRepository.findByUsername("Adrian")).thenReturn(createUser());
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem()));
        ResponseEntity<Cart> response = cartController.removeFromCart
                (createModifyCartRequestNegativeScenarioWrongItem());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
