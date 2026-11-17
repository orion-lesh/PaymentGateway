package orionlesh.paymentgateway.service;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import orionlesh.paymentgateway.model.PaymentEntity;
import orionlesh.paymentgateway.model.PaymentStatus;
import orionlesh.paymentgateway.model.dto.CreatePaymentRequest;
import orionlesh.paymentgateway.model.dto.PaymentDto;
import orionlesh.paymentgateway.model.dto.PaymentEntityMapper;
import orionlesh.paymentgateway.repository.PaymentRepository;
import orionlesh.paymentgateway.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEntityMapper paymentEntityMapper;
    private final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, PaymentEntityMapper paymentEntityMapper, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentEntityMapper = paymentEntityMapper;
        this.userRepository = userRepository;
    }

    public PaymentDto getPayment(Long id) {
        PaymentEntity paymentEntity = findPaymentOrThrow(id);
        return paymentEntityMapper.convertEntityToDto(paymentEntity);
  }

    @Transactional
    public PaymentDto createPayment(CreatePaymentRequest cpq) {

        if(!userRepository.existsById(cpq.userId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + cpq.userId() + "not found");
        }
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
    if (paymentEntity.getPaymentStatus() != PaymentStatus.NEW) {
        throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment status is not new");
    }
        paymentEntity.setPaymentStatus(PaymentStatus.PROCESSED);
        paymentRepository.save(paymentEntity);
        return paymentEntityMapper.convertEntityToDto(paymentEntity);
    }

    private PaymentEntity findPaymentOrThrow(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment with id" + id + "was not found"));
    }
}
