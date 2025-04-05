package com.SecuriDine.OrderService.Entity;

import jakarta.persistence.*;
import com.SecuriDine.OrderService.DTO.RestaurantDTO;
import com.SecuriDine.OrderService.Util.AESUtil;
import com.SecuriDine.OrderService.Util.HMACUtil;

import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "location")
    private String location;

    @Column(name = "hmac_string", nullable = false)
    private String hmac;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menuItems;

    public void computeHMAC() throws Exception {
        String data = restaurantName + location;
        this.hmac = HMACUtil.generateHMAC(data);
    }

    public boolean verifyHMAC() throws Exception {
        String data = restaurantName + location;
        return HMACUtil.verifyHMAC(data, this.hmac);
    }

    public String getName() throws Exception {
        return AESUtil.decrypt(restaurantName);
    }

    public void setName(String restaurantName) throws Exception {
        this.restaurantName = AESUtil.encrypt(restaurantName);
    }

    public String getLocation() throws Exception {
        return AESUtil.decrypt(location);
    }

    public void setLocation(String location) throws Exception {
        this.location = AESUtil.encrypt(location);
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<Menu> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<Menu> menuItems) {
        this.menuItems = menuItems;
    }

    public Restaurant() {}

    public Restaurant(String restaurantName, String location) throws Exception {
        this.restaurantName = AESUtil.encrypt(restaurantName);
        this.location = AESUtil.encrypt(location);
    }

    public RestaurantDTO convertToDTO() throws Exception {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setRestaurantId(this.restaurantId);
        dto.setName(getName());
        dto.setLocation(getLocation());
        return dto;
    }
}
