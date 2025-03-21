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