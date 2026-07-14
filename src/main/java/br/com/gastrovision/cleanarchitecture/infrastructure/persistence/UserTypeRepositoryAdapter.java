package br.com.gastrovision.cleanarchitecture.infrastructure.persistence;

import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import br.com.gastrovision.cleanarchitecture.domain.repository.UserTypeRepository;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.mapper.UserTypeMapper;
import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.repository.UserTypeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserTypeRepositoryAdapter implements UserTypeRepository {

    private final UserTypeJpaRepository jpaRepository;
    private final UserTypeMapper mapper;

    public UserTypeRepositoryAdapter(UserTypeJpaRepository jpaRepository, UserTypeMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public UserType save(UserType userType) {
        return mapper.toDomain(jpaRepository.save(mapper.toJpa(userType)));
    }

    @Override
    public Optional<UserType> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<UserType> findAll() {
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
