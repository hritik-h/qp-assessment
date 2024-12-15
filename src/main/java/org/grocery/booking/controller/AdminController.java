package org.grocery.booking.controller;
import org.grocery.booking.entity.GroceryItem;
import org.grocery.booking.repos.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/grocery-items")
public class AdminController {

    @Autowired
    private GroceryItemRepository repository;

    @PostMapping
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem item) {
        GroceryItem savedItem = repository.save(item);
        return ResponseEntity.status(201).body(savedItem);
    }

    @GetMapping
    public List<GroceryItem> viewAllItems() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> updateItem(@PathVariable Long id, @RequestBody GroceryItem updatedItem) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    item.setPrice(updatedItem.getPrice());
                    item.setDescription(updatedItem.getDescription());
                    GroceryItem savedItem = repository.save(item);
                    return ResponseEntity.ok(savedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/inventory")
    public ResponseEntity<GroceryItem> updateInventory(@PathVariable Long id, @RequestBody int inventoryLevel) {
        return repository.findById(id)
                .map(item -> {
                    item.setInventoryLevel(inventoryLevel);
                    GroceryItem updatedItem = repository.save(item);
                    return ResponseEntity.ok(updatedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
