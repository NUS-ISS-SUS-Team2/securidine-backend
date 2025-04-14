package com.SecuriDine.OrderService.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SecuriDine.OrderService.DTO.RestaurantDTO;
import com.SecuriDine.OrderService.DTO.RestaurantDTOTest;
import com.SecuriDine.OrderService.Repository.RestaurantRepository;
import com.SecuriDine.OrderService.Service.RestaurantService;
import com.SecuriDine.OrderService.Service.RestaurantServiceImpl;

@CrossOrigin
@RestController
public class RestaurantController {

	private RestaurantService restaurantService;
	
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);
	
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
	
	@GetMapping("/restaurant/getAllRestaurants")
	@ResponseBody
    public List<RestaurantDTO> getAllRestaurantsApi() throws Exception{
        System.out.println("getAll Restaurant endpoint works!");
		LOGGER.info("LOG - CALLING Restaurant CONTROLLER");
	    List <RestaurantDTO> getAllRestaurants = restaurantService.getAllRestaurants();
	    System.out.print("AllRestaurants" + getAllRestaurants);
	    return getAllRestaurants;
    }
	
	
    @GetMapping("/restaurant/testEndpoint")
    @ResponseBody
    public RestaurantDTOTest yourEndpoint() {
    	HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "application/text");
    	System.out.println("Test Endpoint works!");
    	RestaurantDTOTest test = new RestaurantDTOTest("This data is from Restaurant Service API!");
        return test;
    }
	
}
