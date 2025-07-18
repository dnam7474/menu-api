package com.simple.menu_api.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userid", nullable = false)
    @JsonProperty("userId")
    private Long userId;

    @Column(name = "ordertime")
    @JsonProperty("orderTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderTime;

    @Column(name = "pickuptime")
    @JsonProperty("pickupTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime pickupTime;

    private String area;

    private String location;

    @Column(precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(precision = 10, scale = 2)
    private BigDecimal tip;

    private String pan;

    @Column(name = "expiry_month")
    @JsonProperty("expiryMonth")
    private Integer expiryMonth;

    @Column(name = "expiry_year")
    @JsonProperty("expiryYear")
    private Integer expiryYear;

    private String status = "new";

    // Constructor that sets defaults
    public Order(Long userId, String area, String location, BigDecimal tax,
                 BigDecimal tip, String pan, Integer expiryMonth, Integer expiryYear) {
        this.userId = userId;
        this.area = area;
        this.location = location;
        this.tax = tax;
        this.tip = tip;
        this.pan = pan;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.orderTime = LocalDateTime.now();
        this.status = "new";
    }
}