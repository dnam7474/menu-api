package com.simple.menu_api.repository;

import com.simple.menu_api.entity.Item;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOrderId(Long orderId);
    List<Item> findByItemId(Long itemId);
    void deleteByOrderId(Long orderId);
}
