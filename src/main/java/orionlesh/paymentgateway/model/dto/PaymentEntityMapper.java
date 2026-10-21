package orionlesh.paymentgateway.model.dto;

import org.springframework.stereotype.Component;
import orionlesh.paymentgateway.model.PaymentEntity;

import java.util.List;

@Component
public class PaymentEntityMapper {
    public PaymentDto convertEntityToDto(PaymentEntity paymentEntity) {
        return new PaymentDto(
                paymentEntity.getPaymentId(),
                paymentEntity.getUserId(),
                paymentEntity.getAmount(),
                paymentEntity.getPaymentStatus(),
                paymentEntity.getCreatedAt(),
                paymentEntity.getUpdatedAt()
        );
    }
    public List<PaymentDto> mapListToDto(List<PaymentEntity> paymentEntities) {
        return paymentEntities.stream()
                .map(this::convertEntityToDto)
                .toList();


    }



}
