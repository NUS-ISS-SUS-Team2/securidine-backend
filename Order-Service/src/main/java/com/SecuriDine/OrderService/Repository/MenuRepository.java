package com.SecuriDine.OrderService.Repository;

import com.SecuriDine.OrderService.Entity.Menu;
import com.SecuriDine.OrderService.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Find all menu items by a specific restaurant
    List<Menu> findByRestaurant(Restaurant restaurant);

    // Find by item name (optional)
    List<Menu> findByItemNameContainingIgnoreCase(String itemName);
}
