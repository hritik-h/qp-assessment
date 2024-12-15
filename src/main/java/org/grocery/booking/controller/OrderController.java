package org.grocery.booking.controller;

import org.grocery.booking.entity.GroceryItem;
import org.grocery.booking.entity.Order;
import org.grocery.booking.repos.GroceryItemRepository;
import org.grocery.booking.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private GroceryItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        for (Order.OrderItem orderItem : order.getItems()) {
            GroceryItem groceryItem = itemRepository.findById(orderItem.getGroceryItemId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));

            if (groceryItem.getInventoryLevel() < orderItem.getQuantity()) {
                throw new RuntimeException("Insufficient inventory for item: " + groceryItem.getName());
            }

            groceryItem.setInventoryLevel(groceryItem.getInventoryLevel() - orderItem.getQuantity());
            itemRepository.save(groceryItem);
        }

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.status(201).body(savedOrder);
    }
}
