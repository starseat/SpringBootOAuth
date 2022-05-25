package spring.boot.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.boot.oauth2.entity.User;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);
}
