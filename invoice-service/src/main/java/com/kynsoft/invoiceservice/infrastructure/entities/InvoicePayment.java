package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_payments")
@Getter
@Setter
@ToString(exclude = "invoice")
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "payment_method", nullable = false, length = 2)
    private String paymentMethod; // Códigos de forma de pago según SRI: 01 (Efectivo), 16 (Tarjeta de Débito), etc.
    
    @Column(name = "payment_method_name")
    private String paymentMethodName;
    
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    
    @Column(name = "time_unit")
    private String timeUnit; // dias, meses, años
    
    @Column(name = "time_value")
    private BigDecimal timeValue; // Valor del plazo
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}