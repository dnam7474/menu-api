package com.simple.menu_api.controller;

import com.simple.menu_api.entity.MenuItem;
import com.simple.menu_api.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menuitems")
@CrossOrigin(origins = "*")
public class MenuItemController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        try {
            List<MenuItem> menuItems = menuItemRepository.findAll();
            return ResponseEntity.ok(menuItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        try {
            Optional<MenuItem> menuItem = menuItemRepository.findById(id);
            if (menuItem.isPresent()) {
                return ResponseEntity.ok(menuItem.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable String category) {
        try {
            List<MenuItem> menuItems = menuItemRepository.findByCategory(category);
            return ResponseEntity.ok(menuItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems() {
        try {
            List<MenuItem> menuItems = menuItemRepository.findByAvailableTrue();
            return ResponseEntity.ok(menuItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/category/{category}/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItemsByCategory(@PathVariable String category) {
        try {
            List<MenuItem> menuItems = menuItemRepository.findByCategoryAndAvailableTrue(category);
            return ResponseEntity.ok(menuItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        try {
            MenuItem savedMenuItem = menuItemRepository.save(menuItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMenuItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem) {
        try {
            if (menuItemRepository.existsById(id)) {
                menuItem.setId(id);
                MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
                return ResponseEntity.ok(updatedMenuItem);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> updateMenuItemAvailability(@PathVariable Long id, @RequestBody Boolean available) {
        try {
            Optional<MenuItem> menuItemOpt = menuItemRepository.findById(id);
            if (menuItemOpt.isPresent()) {
                MenuItem menuItem = menuItemOpt.get();
                menuItem.setAvailable(available);
                MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
                return ResponseEntity.ok(updatedMenuItem);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        try {
            if (menuItemRepository.existsById(id)) {
                menuItemRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}