package com.SecuriDine.OrderService.Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.SecuriDine.OrderService.Util.AESUtil;
import com.SecuriDine.OrderService.Util.HMACUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class StartupDataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public StartupDataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // Sample data
    	List<Map<String, Object>> orders = List.of(
		    Map.of("name", "John Doe", "address", "123 Main St, NY", "price", 49.99f),
		    Map.of("name", "Jane Smith", "address", "789 Park Ave, NY", "price", 29.50f),
		    Map.of("name", "Alice Johnson", "address", "321 5th Ave, LA", "price", 19.75f),
		    Map.of("name", "Bob Lee", "address", "654 Sunset Blvd, CA", "price", 39.99f),
		    Map.of("name", "Eve Adams", "address", "987 Ocean Dr, FL", "price", 59.25f)
		);

		// SQL statement
		String orderSql = "INSERT INTO orders (customer_name, delivery_address, order_date, total_price, hmac_string) VALUES (?, ?, ?, ?, ?)";

		for (Map<String, Object> order : orders) {
		    String name = (String) order.get("name");
		    String address = (String) order.get("address");
		    Float price = (Float) order.get("price");
		    LocalDateTime orderDate = LocalDateTime.now();

		    // Encrypt fields
		    String encryptedName = AESUtil.encrypt(name);
		    String encryptedAddress = AESUtil.encrypt(address);

		    // Generate HMAC
		    String hmac = HMACUtil.generateHMAC(encryptedName + encryptedAddress + price);

		    // Insert into DB
		    jdbcTemplate.update(orderSql, encryptedName, encryptedAddress, orderDate, price, hmac);
		}

		// ------------------------------------------------------------------------------------------------------------------------------
        // Define a list of restaurants
        List<Map<String, String>> restaurants = List.of(
            Map.of("name", "Spice & Soul", "location", "456 Broadway, NY"),
            Map.of("name", "Pizza Palace", "location", "101 Main St, LA"),
            Map.of("name", "Harvest & Heart", "location", "202 Elm St, TX"),
            Map.of("name", "Taco Town", "location", "303 Oak St, AZ"),
            Map.of("name", "Burger Barn", "location", "404 Pine St, CA")
        );

        // SQL insert statement
        String restaurantSql = "INSERT INTO restaurants (restaurant_name, location, hmac_string) VALUES (?, ?, ?)";

        for (Map<String, String> restaurant : restaurants) {
            String name = restaurant.get("name");
            String location = restaurant.get("location");

            // Encrypt fields
            String encryptedName = AESUtil.encrypt(name);
            String encryptedLocation = AESUtil.encrypt(location);

            // Generate HMAC
            String hmac = HMACUtil.generateHMAC(encryptedName + encryptedLocation);

            // Insert into DB
            jdbcTemplate.update(restaurantSql, encryptedName, encryptedLocation, hmac);
        }

        // ------------------------------------------------------------------------------------------------------------------------------
        // Define a list of menu items
        List<Map<String, Object>> menuItems = List.of(
            Map.of("name", "Burger", "desc", "Classic Cheeseburger", "price", 9.99f),
            Map.of("name", "Pizza", "desc", "Pepperoni Pizza", "price", 12.49f),
            Map.of("name", "Pasta", "desc", "Creamy Alfredo Pasta", "price", 10.99f),
            Map.of("name", "Salad", "desc", "Fresh Garden Salad", "price", 7.50f),
            Map.of("name", "Taco", "desc", "Spicy Chicken Taco", "price", 8.75f)
        );

        // SQL insert statement
        String menuItemSql = "INSERT INTO menu_items (item_name, description, price, hmac_string, restaurant_id) VALUES (?, ?, ?, ?, ?)";
        
        for (int i=1; i<5; i++) {
            for (Map<String, Object> item : menuItems) {
                String name = (String) item.get("name");
                String desc = (String) item.get("desc");
                Float price = (Float) item.get("price");

                // Encrypt fields
                String encryptedName = AESUtil.encrypt(name);
                String encryptedDesc = AESUtil.encrypt(desc);

                // Generate HMAC
                String hmac = HMACUtil.generateHMAC(encryptedName + encryptedDesc + price);

                // Insert into DB
                jdbcTemplate.update(menuItemSql, encryptedName, encryptedDesc, price, hmac, i);
            }
        }  

    }
}