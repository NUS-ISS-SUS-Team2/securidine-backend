package com.SecuriDine.OrderService.Service;

import com.SecuriDine.OrderService.Entity.Menu;
import com.SecuriDine.OrderService.Entity.Restaurant;
import com.SecuriDine.OrderService.DTO.RestaurantDTO;
import com.SecuriDine.OrderService.Repository.RestaurantRepository;
import com.SecuriDine.OrderService.Util.AESUtil;
import com.SecuriDine.OrderService.Util.HMACUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Component
@Service
public class RestaurantServiceImpl {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Save a restaurant (create or update)
    public RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO) throws Exception {
        Restaurant restaurant;
        if (restaurantDTO.getRestaurantId() != null) {
            // Update existing restaurant
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantDTO.getRestaurantId());
            if (optionalRestaurant.isPresent()) {
                restaurant = optionalRestaurant.get();
                restaurant.setName(restaurantDTO.getName());
                restaurant.setLocation(restaurantDTO.getLocation());
            } else {
                throw new RuntimeException("Restaurant not found");
            }
        } else {
            // Create new restaurant
            restaurant = new Restaurant(restaurantDTO.getName(), restaurantDTO.getLocation());
        }
        restaurant.computeHMAC();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return savedRestaurant.convertToDTO();
    }

   
    // Read all restaurants
    public List<RestaurantDTO> getAllRestaurants() throws Exception {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        List<RestaurantDTO> restaurantDTOs = restaurants.stream()
                .map(restaurant -> {
                    try {
                        return restaurant.convertToDTO();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return restaurantDTOs;
    }

    // Read a restaurant by ID
    public RestaurantDTO getRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            return restaurant.convertToDTO();
        } else {
            throw new RuntimeException("Restaurant not found");
        }
    }

    // Delete a restaurant
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    // Verify HMAC for a restaurant
    public boolean verifyRestaurantHMAC(Long restaurantId) throws Exception {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            return restaurant.verifyHMAC();
        } else {
            throw new RuntimeException("Restaurant not found");
        }
    }
}
















































