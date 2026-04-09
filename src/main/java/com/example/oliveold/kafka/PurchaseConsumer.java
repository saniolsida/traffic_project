package com.example.oliveold.kafka;

import com.example.oliveold.dto.PurchaseRequest;
import com.example.oliveold.service.ProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PurchaseConsumer
{

    private final ProductService productService;

    public PurchaseConsumer(ProductService productService) {
        this.productService = productService;
    }

    @KafkaListener(topics = "purchase-request", groupId = "oliveold-group", concurrency = "10")
    public void consume(List<PurchaseRequest> requests) { // 1. List 타입으로 변경
        try {
            // 2. 서비스 레이어에 리스트 전체를 넘겨서 배치 처리하도록 호출
            productService.purchaseProductsBatch(requests);

            System.out.println("배치 구매 처리 성공: " + requests.size() + "건");
        } catch (Exception e) {
            // 배치 처리 중 에러 발생 시 로그 (실제 서비스에선 에러가 난 건만 골라내거나 재시도 로직 필요)
            System.err.println("배치 처리 중 오류 발생: " + e.getMessage());
        }
    }
}
