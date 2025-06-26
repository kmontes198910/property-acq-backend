package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.company;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.company.SellerDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_seller")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company; // Relación bidireccional

    @Column(name = "social_security", nullable = true)
    private String socialSecurity;

    @Column(name = "folio_parcel_number", nullable = true)
    private String folioParcelNumber;

    @Column(name = "declare_if_foreing", nullable = true)
    private Boolean declareIfForeing;

    @Column(name = "legal_description", nullable = true)
    private String legalDescription;

    @Column(name = "lender_name", nullable = true)
    private String lenderName;

    @Column(name = "loan_number", nullable = true)
    private String loanNumber;

    public Seller(SellerDto dto) {
        this.id = dto.getId();
        this.socialSecurity = dto.getSocialSecurity();
        this.folioParcelNumber = dto.getFolioParcelNumber();
        this.declareIfForeing = dto.getDeclareIfForeing();
        this.legalDescription = dto.getLegalDescription();
        this.lenderName = dto.getLenderName();
        this.loanNumber = dto.getLoanNumber();
    }

}
