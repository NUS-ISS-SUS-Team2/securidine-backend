package com.SecuriDine.OrderService.Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.SecuriDine.OrderService.Util.AESUtil;
import com.SecuriDine.OrderService.Util.HMACUtil;

import java.time.LocalDateTime;

@Component
public class StartupDataLoader implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public StartupDataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        // Sample data
        String customerName = "John Doe";
        String deliveryAddress = "123 Main St, NY";
        Float totalPrice = 49.99f;
        LocalDateTime orderDate = LocalDateTime.now();

        String restaurantName = "Example Restaurant";
        String restaurantLocation = "456 Broadway, NY";

        // Encrypt restaurant fields
        String encryptedRestaurantName = AESUtil.encrypt(restaurantName);
        String encryptedRestaurantLocation = AESUtil.encrypt(restaurantLocation);

        // Generate HMAC for restaurant
        String restaurantHmac = HMACUtil.generateHMAC(encryptedRestaurantName + encryptedRestaurantLocation);

        // Insert restaurant into database
        String restaurantSql = "INSERT INTO restaurants (restaurant_name, location, hmac_string) VALUES (?, ?, ?)";
        jdbcTemplate.update(restaurantSql, encryptedRestaurantName, encryptedRestaurantLocation, restaurantHmac);

        System.out.println("Restaurant inserted on startup with encryption and HMAC!");

        // Menu data
        String menuItemName = "Burger";
        String menuItemDescription = "Classic Cheeseburger";
        Float menuItemPrice = 9.99f;

        // Encrypt menu item fields
        String encryptedMenuItemName = AESUtil.encrypt(menuItemName);
        String encryptedMenuItemDescription = AESUtil.encrypt(menuItemDescription);

        // Generate HMAC for menu item
        String menuItemHmac = HMACUtil.generateHMAC(encryptedMenuItemName + encryptedMenuItemDescription + menuItemPrice);

        // Insert menu item into database
        String menuItemSql = "INSERT INTO menu_items (item_name, description, price, hmac_string, restaurant_id) VALUES (?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(menuItemSql, encryptedMenuItemName, encryptedMenuItemDescription, menuItemPrice, menuItemHmac, 1);

        System.out.println("Menu item inserted on startup with encryption and HMAC!");


        // Encrypt fields
        String encryptedName = AESUtil.encrypt(customerName);
        String encryptedAddress = AESUtil.encrypt(deliveryAddress);
        
        //Leave price encryption out first as DB stores in float instead of string
        //String encryptedPrice = AESUtil.encrypt(String.valueOf(totalPrice));

        // Generate HMAC
        String hmac = HMACUtil.generateHMAC(encryptedName + encryptedAddress + totalPrice);

        // Insert into database
        String sql = "INSERT INTO orders (customer_name, delivery_address, order_date, total_price, hmac_string) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, encryptedName, encryptedAddress, orderDate, totalPrice, hmac);

        System.out.println("Order inserted on startup with encryption and HMAC!");


        

    }
}