package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.mapper;

import br.com.gastrovision.cleanarchitecture.domain.entity.User;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserTypeMapper userTypeMapper;

    public UserMapper(UserTypeMapper userTypeMapper) {
        this.userTypeMapper = userTypeMapper;
    }

    public UserJpaEntity toJpa(User domain) {
        return new UserJpaEntity(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                userTypeMapper.toJpa(domain.getUserType())
        );
    }

    public User toDomain(UserJpaEntity jpa) {
        return new User(
                jpa.getId(),
                jpa.getName(),
                jpa.getEmail(),
                userTypeMapper.toDomain(jpa.getUserType())
        );
    }
}
