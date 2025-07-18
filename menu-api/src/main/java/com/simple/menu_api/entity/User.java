package com.simple.menu_api.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "first", nullable = false)
    private String first;

    @Column(name = "last", nullable = false)
    private String last;

    private String phone;

    private String email;

    @Column(name = "image_url")
    @JsonProperty("imageUrl")
    private String imageUrl;

    private String pan;

    @Column(name = "expiry_month")
    @JsonProperty("expiryMonth")
    private Integer expiryMonth;

    @Column(name = "expiry_year")
    @JsonProperty("expiryYear")
    private Integer expiryYear;

    private String roles;

    // Custom constructor for common use cases
    public User(String username, String password, String first, String last,
                String phone, String email, String roles) {
        this.username = username;
        this.password = password;
        this.first = first;
        this.last = last;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
    }
}

