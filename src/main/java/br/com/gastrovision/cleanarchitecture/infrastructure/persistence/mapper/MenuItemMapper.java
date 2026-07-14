package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.mapper;

import br.com.gastrovision.cleanarchitecture.domain.entity.MenuItem;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.MenuItemJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    private final RestaurantMapper restaurantMapper;

    public MenuItemMapper(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    public MenuItemJpaEntity toJpa(MenuItem domain) {
        return new MenuItemJpaEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getPrice(),
                domain.getOnSiteOnly(),
                domain.getPhotoPath(),
                restaurantMapper.toJpa(domain.getRestaurant())
        );
    }

    public MenuItem toDomain(MenuItemJpaEntity jpa) {
        return new MenuItem(
                jpa.getId(),
                jpa.getName(),
                jpa.getDescription(),
                jpa.getPrice(),
                jpa.getOnSiteOnly(),
                jpa.getPhotoPath(),
                restaurantMapper.toDomain(jpa.getRestaurant())
        );
    }
}
