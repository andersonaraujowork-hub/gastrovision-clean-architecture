package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.repository;

import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.UserTypeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeJpaRepository extends JpaRepository<UserTypeJpaEntity, Long> {
}