package com.simple.menu_api;

import com.simple.menu_api.controller.OrderController;
import com.simple.menu_api.entity.Order;
import com.simple.menu_api.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        testOrder = Order.builder()
                .id(1001L)
                .userId(3L)
                .area("Theater 1")
                .location("Table 37")
                .tax(new BigDecimal("5.33"))
                .tip(new BigDecimal("12.93"))
                .status("completed")
                .orderTime(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldReturnOrder1001Successfully() throws Exception {
        // Given
        when(orderRepository.findById(1001L)).thenReturn(Optional.of(testOrder));

        // When & Then
        mockMvc.perform(get("/api/orders/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1001))
                .andExpect(jsonPath("$.userid").value(3))
                .andExpect(jsonPath("$.status").value("completed"))
                .andExpect(jsonPath("$.area").value("Theater 1"));

        verify(orderRepository, times(1)).findById(1001L);
    }

    @Test
    void shouldReturnOrdersByUserId() throws Exception {
        // Given
        List<Order> userOrders = Arrays.asList(testOrder);
        when(orderRepository.findByUserId(3L)).thenReturn(userOrders);

        // When & Then
        mockMvc.perform(get("/api/orders/user/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userid").value(3));

        verify(orderRepository, times(1)).findByUserId(3L);
    }

    @Test
    void shouldReturn404ForNonExistentOrder() throws Exception {
        // Given
        when(orderRepository.findById(9999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/orders/9999"))
                .andExpect(status().isNotFound());

        verify(orderRepository, times(1)).findById(9999L);
    }
}
