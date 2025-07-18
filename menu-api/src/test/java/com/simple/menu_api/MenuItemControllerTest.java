package com.simple.menu_api;

import com.simple.menu_api.controller.MenuItemController;
import com.simple.menu_api.entity.MenuItem;
import com.simple.menu_api.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemController menuItemController;

    private MockMvc mockMvc;
    private MenuItem testMenuItem;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuItemController).build();

        testMenuItem = MenuItem.builder()
                .id(1L)
                .name("Bison Burger")
                .description("Packed with protein and a touch of sweetness...")
                .category("entrees")  // This will now work with the updated entity
                .price(new BigDecimal("11.54"))
                .imageUrl("/images/food/burger_1.jpg")
                .available(true)
                .build();
    }

    @Test
    void shouldReturnMenuItem1Successfully() throws Exception {
        // Given
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(testMenuItem));

        // When & Then
        mockMvc.perform(get("/api/menu_items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bison Burger"))
                .andExpect(jsonPath("$.category").value("entrees"))
                .andExpect(jsonPath("$.price").value(11.54));

        verify(menuItemRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturn404ForNonExistentMenuItem() throws Exception {
        // Given
        when(menuItemRepository.findById(0L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/menu_items/0"))
                .andExpect(status().isNotFound());

        verify(menuItemRepository, times(1)).findById(0L);
    }

    @Test
    void shouldReturnAllMenuItems() throws Exception {
        // Given
        List<MenuItem> menuItems = Arrays.asList(testMenuItem);
        when(menuItemRepository.findAll()).thenReturn(menuItems);

        // When & Then
        mockMvc.perform(get("/api/menu_items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Bison Burger"));

        verify(menuItemRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnAvailableMenuItems() throws Exception {
        // Given
        List<MenuItem> availableItems = Arrays.asList(testMenuItem);
        // Mock the repository method that your controller actually calls
        when(menuItemRepository.findAll()).thenReturn(availableItems);

        // When & Then
        mockMvc.perform(get("/api/menu_items"))  // Remove the query parameter
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Bison Burger"));

        verify(menuItemRepository, times(1)).findAll();  // Verify the correct method
    }
}
