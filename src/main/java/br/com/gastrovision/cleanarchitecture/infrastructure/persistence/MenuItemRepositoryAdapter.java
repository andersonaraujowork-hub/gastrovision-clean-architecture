package br.com.gastrovision.cleanarchitecture.infrastructure.persistence;

import br.com.gastrovision.cleanarchitecture.domain.entity.MenuItem;
import br.com.gastrovision.cleanarchitecture.domain.repository.MenuItemRepository;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.mapper.MenuItemMapper;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.repository.MenuItemJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MenuItemRepositoryAdapter implements MenuItemRepository {

    private final MenuItemJpaRepository jpaRepository;
    private final MenuItemMapper mapper;

    public MenuItemRepositoryAdapter(MenuItemJpaRepository jpaRepository, MenuItemMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(menuItem)));
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MenuItem> findByRestaurantId(Long restaurantId) {
        return jpaRepository.findByRestaurantId(restaurantId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}