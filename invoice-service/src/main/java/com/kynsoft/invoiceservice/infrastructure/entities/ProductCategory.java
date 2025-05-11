package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "product_categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private Boolean status;
}