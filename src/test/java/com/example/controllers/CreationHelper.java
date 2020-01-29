package com.example.controllers;

import com.example.model.persistence.Cart;
import com.example.model.persistence.Item;
import com.example.model.persistence.User;
import com.example.model.persistence.UserOrder;
import com.example.model.requests.CreateUserRequest;
import com.example.model.requests.ModifyCartRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


abstract class CreationHelper {


    User createUser() {
        User user = new User();
        user.setId(1L);
        user.setPassword("AdrianAdrian");
        user.setUsername("Adrian");
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotal(new BigDecimal(100));
        cart.setUser(user);
        List<Item> items = new ArrayList<>();
        while (items.size() <= 5) {
            Item item = new Item();
            item.setDescription("Generating random items");
            item.setId((long) items.size() + 1);
            item.setName("Random item");
            item.setPrice(new BigDecimal(50));
            items.add(item);
        }
        cart.setItems(items);
        user.setCart(cart);
        return user;
    }

    CreateUserRequest createUserRequest() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("Adrian");
        userRequest.setPassword("AdrianAdrian");
        userRequest.setConfirmPassword("AdrianAdrian");
        return userRequest;
    }

    CreateUserRequest createUserRequestNegativeScenario() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("Adrian");
        userRequest.setPassword("password");
        userRequest.setConfirmPassword("password123");
        return userRequest;
    }

    CreateUserRequest createUserRequestNegativeScenario2() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("");
        userRequest.setPassword("");
        userRequest.setConfirmPassword("");
        return userRequest;
    }

    CreateUserRequest createUserRequestNegativeScenario3() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("Adrian");
        userRequest.setPassword("Adrian1234!");
        userRequest.setConfirmPassword("Adrian1234!");
        return userRequest;
    }

    Item createItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("createItem");
        item.setDescription("createItemDesc");
        item.setPrice(new BigDecimal(15));
        return item;
    }

    List<Item> createItemsList() {
        List<Item> items = new ArrayList<>();
        while(items.size() <= 5) {
            items.add(createItem());
        }
        return items;
    }

     ModifyCartRequest createModifyCartRequest() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(5);
        modifyCartRequest.setUsername("Adrian");
        return modifyCartRequest;
    }

    ModifyCartRequest createModifyCartRequestNegativeScenarioWrongName() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(5);
        modifyCartRequest.setUsername("AdrianAdrian");
        return modifyCartRequest;
    }

    ModifyCartRequest createModifyCartRequestNegativeScenarioWrongItem() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(150L);
        modifyCartRequest.setQuantity(5);
        modifyCartRequest.setUsername("Adrian");
        return modifyCartRequest;
    }

    List<UserOrder> createUserOrders() {
        List<UserOrder> userOrders = new ArrayList<>();
        while (userOrders.size() <= 5) {
            UserOrder order = new UserOrder();
            order.setId((long) userOrders.size() + 1);
            order.setUser(createUser());
            List<Item> items = new ArrayList<>();
            while (items.size() <= 5) {
                Item item = new Item();
                item.setId((long) items.size() + 1);
                item.setDescription("createUserOrders Item");
                item.setName("userOrderItem");
                item.setPrice(new BigDecimal(15));
                items.add(item);
            }
            order.setItems(items);
            order.setTotal(new BigDecimal(75));
            userOrders.add(order);
        }
        return userOrders;
    }
}

