package orionlesh.paymentgateway.model.dto;

import orionlesh.paymentgateway.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDto(
        Long paymentId,
        Long userId,
        BigDecimal amount,
        PaymentStatus paymentStatus,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {

}
