package al.run.userservice.persistence.repository;

import al.run.userservice.persistence.entity.Role;
import al.run.userservice.persistence.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum roleEnum);
}
