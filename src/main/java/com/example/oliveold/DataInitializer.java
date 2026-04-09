//package com.example.oliveold;
//
//import com.example.oliveold.entity.Product;
//import com.example.oliveold.repository.ProductRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final ProductRepository productRepository;
//
//    public DataInitializer(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        if (productRepository.count() == 0) {
//            productRepository.save(new Product(
//                    "연작",
//                    "전초 컨센트레이트 30ml",
//                    "[4월올영픽/10ml증정기획]",
//                    25900,
//                    50,
//                    "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/400/10/0000/0021/A00000021394369ko.jpg?l=ko"
//            ));
//
//            productRepository.save(new Product(
//                    "메디힐",
//                    "더마 패드 200매",
//                    "[1위패드] 메디힐 더마 패드 200매",
//                    28900,
//                    30,
//                    "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/400/10/0000/0017/A000000171427216ko.png?l=ko"
//            ));
//
//            productRepository.save(new Product(
//                    "성분에디터",
//                    "실크펩타이드 EGF 하트핏 볼륨 리프팅 앰플 40ml",
//                    "[4월올영픽/에스테틱 PICK/녹는실리프팅]",
//                    22900,
//                    20,
//                    "https://image.oliveyoung.co.kr/cfimages/cf-goods/uploads/images/thumbnails/400/10/0000/0023/A00000023517939ko.jpg?l=ko"
//            ));
//        }
//    }
//}