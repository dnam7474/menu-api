package com.simple.menu_api;

import com.simple.menu_api.controller.ItemController;
import com.simple.menu_api.entity.Item;
import com.simple.menu_api.repository.ItemRepository;
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
class ItemControllerTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;
    private Item testItem;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();

        testItem = Item.builder()
                .id(1L)
                .orderId(1001L)
                .itemId(20L)
                .price(new BigDecimal("5.10"))
                .firstName("Nora")
                .notes("")
                .build();
    }

    @Test
    void shouldReturnOrderItem1Successfully() throws Exception {
        // Given
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem));

        // When & Then
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderid").value(1001))
                .andExpect(jsonPath("$.itemid").value(20))
                .andExpect(jsonPath("$.firstname").value("Nora"));

        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnItemsByOrderId() throws Exception {
        // Given
        List<Item> orderItems = Arrays.asList(testItem);
        when(itemRepository.findByOrderId(1001L)).thenReturn(orderItems);

        // When & Then
        mockMvc.perform(get("/api/items/order/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].orderid").value(1001));

        verify(itemRepository, times(1)).findByOrderId(1001L);
    }

    @Test
    void shouldReturn404ForNonExistentItem() throws Exception {
        // Given
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/items/999"))
                .andExpect(status().isNotFound());

        verify(itemRepository, times(1)).findById(999L);
    }
}
