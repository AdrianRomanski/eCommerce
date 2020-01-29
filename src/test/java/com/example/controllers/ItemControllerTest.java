package com.example.controllers;

import com.example.model.persistence.Item;
import com.example.model.persistence.repositories.ItemRepository;
import com.example.demo.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest extends CreationHelper {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void validateGetItemById() {
        Item item = createItem();
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        final ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals("createItem", responseBody.getName());
    }

    @Test
    public void validateGetItemsByName() {
        List<Item> items = createItemsList();
        when(itemRepository.findByName("createItem")).thenReturn(items);
        final ResponseEntity<List<Item>> response = itemController.getItemsByName("createItem");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> responseBodyItems = response.getBody();
        assertNotNull(responseBodyItems);
        assertEquals("createItem", responseBodyItems.get(0).getName());
    }

    @Test
    public void validateGetItems() {
        List<Item> items = createItemsList();
        when(itemRepository.findAll()).thenReturn(items);
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> responseBodyItems = response.getBody();
        assertNotNull(responseBodyItems);
        assertEquals(items.get(1).getPrice(), responseBodyItems.get(1).getPrice());
    }
}

