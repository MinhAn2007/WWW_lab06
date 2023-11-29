package vn.edu.iuh.fit.lab06.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.lab06.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPasswordHash(String email, String password);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}