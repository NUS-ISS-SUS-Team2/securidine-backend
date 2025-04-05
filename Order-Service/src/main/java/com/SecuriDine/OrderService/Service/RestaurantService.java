package com.SecuriDine.OrderService.Service;

import java.util.List;
import java.util.Optional;

import com.SecuriDine.OrderService.DTO.RestaurantDTO;
import com.SecuriDine.OrderService.Entity.Restaurant;

import org.springframework.stereotype.Service;

public interface RestaurantService {

    List<RestaurantDTO> getAllRestaurants();
    
    
    RestaurantDTO getRestaurantById(Long restaurantId);

   
    void deleteRestaurant(Long restaurantId);

	
    RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO);

	

}

