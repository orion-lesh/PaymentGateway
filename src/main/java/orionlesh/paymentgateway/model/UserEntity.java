package orionlesh.paymentgateway.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private List<PaymentEntity> payments = new ArrayList<>();

    public UserEntity(String email) {
        this.email = email;
    }
}
