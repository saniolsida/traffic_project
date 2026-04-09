package com.example.oliveold.service;

import com.example.oliveold.dto.PurchaseRequest;
import com.example.oliveold.entity.Product;
import com.example.oliveold.repository.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final StringRedisTemplate redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProductRepository productRepository;

    // 생성자 주입 (Redis와 Kafka 템플릿 추가)
    public ProductService(ProductRepository productRepository,
                          StringRedisTemplate redisTemplate,
                          KafkaTemplate<String, String> kafkaTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 1. 유저의 구매 요청을 처리 (Redis 선검증)
     */
    public void createPurchaseOrder(Long productId) {
        String key = "product:stock:" + productId;
        System.out.println("구매 프로세스 시작: " + productId);

        // Redis에서 재고 1 감소 (Atomic 연산)
        Long remainStock = redisTemplate.opsForValue().decrement(key);

        if (remainStock != null && remainStock >= 0) {
            // 재고가 있으므로 카프카에 메시지 전송 (비동기 처리 시작)
            kafkaTemplate.send("purchase-request", String.valueOf(productId));
            System.out.println("구매 요청 성공 (Redis 차감 완료): " + remainStock);
        } else {
            // 재고가 없으면 Redis 값을 다시 복구(옵션)하거나 즉시 예외 발생
            // decrement로 인해 마이너스가 된 값을 다시 0으로 맞추기 위해 increment 호출 가능
            if (remainStock != null && remainStock < 0) {
                redisTemplate.opsForValue().increment(key);
            }
            throw new IllegalStateException("품절되었습니다. (Redis에서 차단)");
        }
    }

    /**
     * 2. 카프카 컨슈머가 호출하는 최종 DB 반영 로직 (배치)
     */
    @Transactional
    public void purchaseProductsBatch(List<PurchaseRequest> requests) {
        if (requests.isEmpty()) return;

        List<Long> productIds = requests.stream()
                .map(PurchaseRequest::getProductId)
                .collect(Collectors.toList());

        // 이미 Redis에서 걸러진 녀석들이라 DB에서는 훨씬 가볍게 업데이트만 수행
        productRepository.decreaseStockBatch(productIds);
    }
    public List<Product> findAllProducts() {

        return productRepository.findAll();

    }
}