package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.repository;

import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, Long> {
}