package al.run.userservice.persistence.entity;

import al.run.userservice.persistence.entity.enums.Gender;
import al.run.userservice.persistence.entity.enums.RoleEnum;
import al.run.userservice.persistence.repository.RoleRepository;
import al.run.userservice.util.SpringApplicationContext;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Gender gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    public void setRoleByRoleEnum(RoleEnum roleEnum) {
        SpringApplicationContext.getBean(RoleRepository.class).findByRole(roleEnum).ifPresent(role -> this.setRole(role));
    }

}
