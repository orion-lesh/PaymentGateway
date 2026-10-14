package orionlesh.paymentgateway.service;

import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import orionlesh.paymentgateway.model.PaymentEntity;
import orionlesh.paymentgateway.model.PaymentStatus;
import orionlesh.paymentgateway.model.dto.CreatePaymentRequest;
import orionlesh.paymentgateway.model.dto.PaymentDto;
import orionlesh.paymentgateway.repository.PaymentRepository;

import java.math.BigDecimal;

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    private PaymentDto convertEntityToDto(PaymentEntity paymentEntity) {
        return new PaymentDto(
                paymentEntity.getPaymentId(),
                paymentEntity.getUserId(),
                paymentEntity.getAmount(),
                paymentEntity.getPaymentStatus(),
                paymentEntity.getCreatedAt(),
                paymentEntity.getUpdatedAt()
        );
    }

    public PaymentDto getPayment(Long id) {
        PaymentEntity paymentEntity = findPaymentOrThrow(id);
        return convertEntityToDto(paymentEntity);
  }

    @Transactional
    public PaymentDto createPayment(CreatePaymentRequest cpq) {
        if (MAX_AMOUNT.compareTo(cpq.amount()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount is too large");
        }
        PaymentEntity paymentEntity = new PaymentEntity(cpq.userId(), cpq.amount(), PaymentStatus.NEW);
        paymentRepository.save(paymentEntity);
        return convertEntityToDto(paymentEntity);
    }

    @Transactional
    public PaymentDto confirmPayment(Long id) {
        PaymentEntity paymentEntity = findPaymentOrThrow(id);
        paymentEntity.setPaymentStatus(PaymentStatus.PROCESSED);
        paymentRepository.save(paymentEntity);
        return convertEntityToDto(paymentEntity);
    }

    public PaymentEntity findPaymentOrThrow(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment with id" + id + "was not found"));
    }
}
