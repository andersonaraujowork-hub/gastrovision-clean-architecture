package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_types")
public class UserTypeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public UserTypeJpaEntity() {}

    public UserTypeJpaEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
