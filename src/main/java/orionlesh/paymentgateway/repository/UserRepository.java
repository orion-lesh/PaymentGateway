package orionlesh.paymentgateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import orionlesh.paymentgateway.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmailIgnoreCase(String email);
}
