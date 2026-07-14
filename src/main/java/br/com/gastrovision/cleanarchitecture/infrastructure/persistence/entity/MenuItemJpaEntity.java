package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItemJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean onSiteOnly;

    private String photoPath;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantJpaEntity restaurant;

    public MenuItemJpaEntity() {}

    public MenuItemJpaEntity(Long id, String name, String description, BigDecimal price, Boolean onSiteOnly, String photoPath, RestaurantJpaEntity restaurant) {
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
    public RestaurantJpaEntity getRestaurant() { return restaurant; }
    public void setRestaurant(RestaurantJpaEntity restaurant) { this.restaurant = restaurant; }
}