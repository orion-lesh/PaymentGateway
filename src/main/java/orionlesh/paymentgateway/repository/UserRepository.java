package orionlesh.paymentgateway.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import orionlesh.paymentgateway.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = {"payments"})
    Optional<UserEntity> findWithPaymentsById(Long id);
}
