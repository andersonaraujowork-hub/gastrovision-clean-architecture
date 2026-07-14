package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.mapper;

import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.UserTypeJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTypeMapper {

    public UserTypeJpaEntity toJpa(UserType domain) {
        return new UserTypeJpaEntity(domain.getId(), domain.getName());
    }

    public UserType toDomain(UserTypeJpaEntity jpa) {
        return new UserType(jpa.getId(), jpa.getName());
    }
}