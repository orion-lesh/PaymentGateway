package orionlesh.paymentgateway.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name="payments")
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue
    private Long paymentId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;


    public PaymentEntity(Long userId, BigDecimal amount, PaymentStatus paymentStatus) {
        this.userId = userId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;

    }


}
