package al.run.userservice.persistence.repository;

import al.run.userservice.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Nullable String email);

    Optional<User> findByUsername(@Nullable String username);
}
