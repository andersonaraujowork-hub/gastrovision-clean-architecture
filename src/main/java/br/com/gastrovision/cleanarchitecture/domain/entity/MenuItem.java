package br.com.gastrovision.cleanarchitecture.domain.entity;

import java.math.BigDecimal;

public class MenuItem {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean onSiteOnly;
    private String photoPath;
    private Restaurant restaurant;

    public MenuItem() {}

    public MenuItem(Long id, String name, String description, BigDecimal price, Boolean onSiteOnly, String photoPath, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.onSiteOnly = onSiteOnly;
        this.photoPath = photoPath;
        this.restaurant = restaurant;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Boolean getOnSiteOnly() { return onSiteOnly; }
    public void setOnSiteOnly(Boolean onSiteOnly) { this.onSiteOnly = onSiteOnly; }
    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }
    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
}
