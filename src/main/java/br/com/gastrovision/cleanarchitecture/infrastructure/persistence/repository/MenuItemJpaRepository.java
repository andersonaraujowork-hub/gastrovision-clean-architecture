package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.repository;

import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.MenuItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemJpaEntity, Long> {
    List<MenuItemJpaEntity> findByRestaurantId(Long restaurantId);
}