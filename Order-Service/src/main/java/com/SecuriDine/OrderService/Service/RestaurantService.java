package com.SecuriDine.OrderService.Service;

import java.util.List;
import java.util.Optional;

import com.SecuriDine.OrderService.DTO.RestaurantDTO;
import com.SecuriDine.OrderService.Entity.Restaurant;

import org.springframework.stereotype.Service;

public interface RestaurantService {

    List<RestaurantDTO> getAllRestaurants() throws Exception;
    
    
    RestaurantDTO getRestaurantById(Long restaurantId) throws Exception;

   
    void deleteRestaurant(Long restaurantId) throws Exception;

	
    RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO) throws Exception;


	boolean verifyRestaurantHMAC(Long restaurantId) throws Exception;

	

}

