package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.mapper;

import br.com.gastrovision.cleanarchitecture.domain.entity.Restaurant;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.RestaurantJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    private final UserMapper userMapper;

    public RestaurantMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RestaurantJpaEntity toJpa(Restaurant domain) {
        return new RestaurantJpaEntity(
                domain.getId(),
                domain.getName(),
                domain.getAddress(),
                domain.getCuisineType(),
                domain.getOpeningHours(),
                userMapper.toJpa(domain.getOwner())
        );
    }

    public Restaurant toDomain(RestaurantJpaEntity jpa) {
        return new Restaurant(
                jpa.getId(),
                jpa.getName(),
                jpa.getAddress(),
                jpa.getCuisineType(),
                jpa.getOpeningHours(),
                userMapper.toDomain(jpa.getOwner())
        );
    }
}
