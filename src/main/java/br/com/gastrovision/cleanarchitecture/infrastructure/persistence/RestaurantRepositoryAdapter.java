package br.com.gastrovision.cleanarchitecture.infrastructure.persistence;

import br.com.gastrovision.cleanarchitecture.domain.entity.Restaurant;
import br.com.gastrovision.cleanarchitecture.domain.repository.RestaurantRepository;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.mapper.RestaurantMapper;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.repository.RestaurantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RestaurantRepositoryAdapter implements RestaurantRepository {

    private final RestaurantJpaRepository jpaRepository;
    private final RestaurantMapper mapper;

    public RestaurantRepositoryAdapter(RestaurantJpaRepository jpaRepository, RestaurantMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(restaurant)));
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
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
