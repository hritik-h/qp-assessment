package org.grocery.booking.controller;

import org.grocery.booking.entity.GroceryItem;
import org.grocery.booking.repos.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grocery-items")
public class UserController {

    @Autowired
    private GroceryItemRepository repository;

    @GetMapping
    public List<GroceryItem> getAvailableItems() {
        return repository.findByInventoryLevelGreaterThan(0);
    }
}

