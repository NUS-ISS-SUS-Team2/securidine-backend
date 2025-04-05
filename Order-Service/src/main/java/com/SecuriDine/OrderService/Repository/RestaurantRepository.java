package com.SecuriDine.OrderService.Repository;

import com.SecuriDine.OrderService.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Custom query example (if needed later)
    Restaurant findById(String name);
}
