package com.example.oliveold.redis;

import com.example.oliveold.entity.Product;
import com.example.oliveold.repository.ProductRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisInventoryLoader implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final StringRedisTemplate redisTemplate;

    public RedisInventoryLoader(ProductRepository productRepository, StringRedisTemplate redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        // 1. 모든 상품 조회
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            String key = "product:stock:" + product.getId();
            // 2. Redis에 재고 저장 (이미 있으면 덮어씌움)
            redisTemplate.opsForValue().set(key, String.valueOf(product.getStock()));
        }
        System.out.println(">>> Redis 재고 로딩 완료: " + products.size() + "건");
        System.out.println(">>> 프로젝트 수정 여부 확인");
    }
}
