package orionlesh.paymentgateway.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import orionlesh.paymentgateway.model.UserEntity;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = {"payments"})
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAllWithPayments(); //for eager load
}
