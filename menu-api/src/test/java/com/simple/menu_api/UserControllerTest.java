package com.simple.menu_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.menu_api.controller.UserController;
import com.simple.menu_api.entity.User;
import com.simple.menu_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User testUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        testUser = User.builder()
                .id(1L)
                .username("admin")
                .password("hashedPassword")
                .first("Administrator")
                .last("User")
                .email("admin@daam.com")
                .phone("(555) 943-2230")
                .roles("ROLE_ADMIN")
                .build();
    }

    @Test
    void shouldReturnUser1Successfully() throws Exception {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When & Then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.first").value("Administrator"));

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturn404ForNonExistentUser() throws Exception {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void shouldCreateUserSuccessfully() throws Exception {
        // Given
        User newUser = User.builder()
                .username("testuser")
                .password("password")
                .first("Test")
                .last("User")
                .email("test@example.com")
                .roles("ROLE_USER")
                .build();

        User savedUser = User.builder()
                .id(2L)
                .username("testuser")
                .password("hashedPassword")
                .first("Test")
                .last("User")
                .email("test@example.com")
                .roles("ROLE_USER")
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When & Then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        // Given
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].username").value("admin"));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {
        // Given
        User updatedUser = User.builder()
                .id(1L)
                .username("admin")
                .password("newHashedPassword")
                .first("Updated")
                .last("Admin")
                .email("updated@daam.com")
                .roles("ROLE_ADMIN")
                .build();

        // Mock what the controller actually calls
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser)); // Controller calls this first
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);  // Then saves

        // When & Then
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first").value("Updated"));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentUser() throws Exception {
        // Given
        User updatedUser = User.builder()
                .id(999L)
                .username("nonexistent")
                .password("password")
                .first("Test")
                .last("User")
                .email("test@example.com")
                .roles("ROLE_USER")
                .build();

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/api/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, times(0)).save(any(User.class)); // Should not save
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentUser() throws Exception {
        // Given
        when(userRepository.existsById(999L)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound());

        verify(userRepository, times(1)).existsById(999L);
        verify(userRepository, times(0)).deleteById(999L); // Should not delete
    }
}
