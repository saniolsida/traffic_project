package com.example.oliveold.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;          // 브랜드
    private String name;           // 상품명
    private String description;    // 설명
    private int price;             // 판매가
    @Setter
    private int stock;             // 재고
    private String imageUrl;       // 이미지 주소

    public Product() {
    }

    public Product(String brand, String name, String description, int price, int stock, String imageUrl) {
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isAvailable() {
        return stock > 0;
    }
}