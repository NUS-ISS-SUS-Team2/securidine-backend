package com.SecuriDine.OrderService.DTO;

import java.util.List;

public class RestaurantDTO {

    private Long restaurantId;
    private String name;
    private String location;
    private List<MenuDTO> menuItems;  

    public RestaurantDTO() {}

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<MenuDTO> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuDTO> menuItems) {
        this.menuItems = menuItems;
    }
}
