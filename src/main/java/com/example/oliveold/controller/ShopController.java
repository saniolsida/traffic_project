package com.example.oliveold.controller;

import com.example.oliveold.entity.Product;
import com.example.oliveold.kafka.PurchaseProducer;
import com.example.oliveold.repository.ProductRepository;
import com.example.oliveold.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
@Controller
public class ShopController {

    private final ProductService  productService;
    private final PurchaseProducer purchaseProducer;

    public ShopController(ProductService productService, PurchaseProducer purchaseProducer) {
        this.productService = productService;
        this.purchaseProducer = purchaseProducer;
    }

    @GetMapping("/")
    public String ShopMainPage(Model model) {
        // 서비스에게 상품 목록을 가져오라고 시킵니다.
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "shop";
    }

    @PostMapping("/purchase/{id}")
    @ResponseBody // JSON이나 직접 응답을 보낼 때 필요
    public ResponseEntity<String> purchase(@PathVariable Long id) {
        try {
            productService.createPurchaseOrder(id);
            // 성공 시 200 OK 또는 202 Accepted
            return ResponseEntity.ok("구매 성공");
        } catch (IllegalStateException e) {
            // 품절 시 410 Gone 또는 400 Bad Request
            // k6는 4xx 응답을 'HTTP Fail'로 집계합니다.
            return ResponseEntity.status(HttpStatus.GONE).body("품절되었습니다.");
        } catch (Exception e) {
            log.error(">>>> [컨트롤러 에러 발생!!] 에러 종류: {}, 메시지: {}",
                    e.getClass().getSimpleName(), e.getMessage());
            log.error(">>>> [에러 상세 내용]", e); // 전체 스택 트레이스 로깅
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }
}