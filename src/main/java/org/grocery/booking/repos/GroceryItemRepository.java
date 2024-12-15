package org.grocery.booking.repos;

import org.grocery.booking.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
    List<GroceryItem> findByInventoryLevelGreaterThan(int inventoryLevel);
}
