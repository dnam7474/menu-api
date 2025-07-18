package com.simple.menu_api.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderid", nullable = false)
    @JsonProperty("orderid")
    private Long orderId;

    @Column(name = "itemid", nullable = false)
    @JsonProperty("itemid")
    private Long itemId;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "firstname")
    @JsonProperty("firstname")
    private String firstName;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Custom constructor for common use cases
    public Item(Long orderId, Long itemId, BigDecimal price, String firstName, String notes) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.price = price;
        this.firstName = firstName;
        this.notes = notes;
    }
}
