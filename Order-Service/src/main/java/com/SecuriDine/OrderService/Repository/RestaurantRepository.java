package com.SecuriDine.OrderService.Repository;

import com.SecuriDine.OrderService.Entity.Restaurant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Custom query example (if needed later)
	@Query("SELECT r FROM Restaurant r WHERE r.restaurantId = :restaurantId")
    Optional<Restaurant> findById(@Param("restaurantId")Long id);

}
