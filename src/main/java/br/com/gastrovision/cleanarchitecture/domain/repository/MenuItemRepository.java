package br.com.gastrovision.cleanarchitecture.domain.repository;

import br.com.gastrovision.cleanarchitecture.domain.entity.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);
    Optional<MenuItem> findById(Long id);
    List<MenuItem> findByRestaurantId(Long restaurantId);
    void deleteById(Long id);
    boolean existsById(Long id);
}