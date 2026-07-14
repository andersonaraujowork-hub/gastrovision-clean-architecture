package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.repository;

import br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
}