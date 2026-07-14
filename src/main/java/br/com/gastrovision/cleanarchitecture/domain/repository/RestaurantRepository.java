package br.com.gastrovision.cleanarchitecture.domain.repository;

import br.com.gastrovision.cleanarchitecture.domain.entity.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    List<Restaurant> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}