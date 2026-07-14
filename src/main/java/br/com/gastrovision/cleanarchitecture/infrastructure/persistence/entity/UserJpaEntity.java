package br.com.gastrovision.cleanarchitecture.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_type_id", nullable = false)
    private UserTypeJpaEntity userType;

    public UserJpaEntity() {}

    public UserJpaEntity(Long id, String name, String email, UserTypeJpaEntity userType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserTypeJpaEntity getUserType() { return userType; }
    public void setUserType(UserTypeJpaEntity userType) { this.userType = userType; }
}
