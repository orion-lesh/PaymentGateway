package orionlesh.paymentgateway.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import orionlesh.paymentgateway.model.PaymentEntity;
import orionlesh.paymentgateway.model.PaymentStatus;
import orionlesh.paymentgateway.model.dto.CreatePaymentRequest;
import orionlesh.paymentgateway.model.dto.PaymentDto;
import orionlesh.paymentgateway.model.dto.PaymentEntityMapper;
import orionlesh.paymentgateway.repository.PaymentRepository;

import java.math.BigDecimal;

@Component
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEntityMapper paymentEntityMapper;
    private final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");

    public PaymentService(PaymentRepository paymentRepository, PaymentEntityMapper paymentEntityMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentEntityMapper = paymentEntityMapper;
    }

    public PaymentDto getPayment(Long id) {
        PaymentEntity paymentEntity = findPaymentOrThrow(id);
        return paymentEntityMapper.convertEntityToDto(paymentEntity);
  }

    @Transactional
    public PaymentDto createPayment(CreatePaymentRequest cpq) {
        if (cpq.amount().compareTo(MAX_AMOUNT) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount is too large");
        }
        PaymentEntity paymentEntity = new PaymentEntity(cpq.userId(), cpq.amount(), PaymentStatus.NEW);
        paymentRepository.save(paymentEntity);
        return paymentEntityMapper.convertEntityToDto(paymentEntity);
    }

    @Transactional
    public PaymentDto confirmPayment(Long id) {
        PaymentEntity paymentEntity = findPaymentOrThrow(id);
        paymentEntity.setPaymentStatus(PaymentStatus.PROCESSED);
        paymentRepository.save(paymentEntity);
        return paymentEntityMapper.convertEntityToDto(paymentEntity);
    }

    private PaymentEntity findPaymentOrThrow(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment with id" + id + "was not found"));
    }
}
