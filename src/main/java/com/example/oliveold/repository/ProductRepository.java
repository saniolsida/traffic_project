package com.example.oliveold.repository;

import com.example.oliveold.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findWithLockById(Long id);

    @Modifying(clearAutomatically = true) // 벌크 연산 후 영속성 컨텍스트 초기화
    @Query("UPDATE Product p SET p.stock = p.stock - 1 WHERE p.id IN :productIds AND p.stock > 0")
    int decreaseStockBatch(@Param("productIds") List<Long> productIds);
}
