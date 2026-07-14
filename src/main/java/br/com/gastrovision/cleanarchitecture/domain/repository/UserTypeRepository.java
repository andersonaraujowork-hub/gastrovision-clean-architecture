package br.com.gastrovision.cleanarchitecture.domain.repository;

import br.com.gastrovision.cleanarchitecture.domain.entity.UserType;
import java.util.List;
import java.util.Optional;

public interface UserTypeRepository {
    UserType save(UserType userType);
    Optional<UserType> findById(Long id);
    List<UserType> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
