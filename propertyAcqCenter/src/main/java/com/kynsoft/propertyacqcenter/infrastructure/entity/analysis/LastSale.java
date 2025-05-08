package com.kynsoft.propertyacqcenter.infrastructure.entity.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.analysis.LastSaleDto;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Analysis;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "last_sale")
public class LastSale {
    @Id
    private UUID id;

    private String publicRecord;
    private String mls;
    private String documentType;

    @OneToOne(mappedBy = "lastSale")
    private Analysis analysis;

    public LastSale(LastSaleDto dto) {
        this.id = dto.getId();
        this.publicRecord = dto.getPublicRecord();
        this.mls = dto.getMls();
        this.documentType = dto.getDocumentType();
        this.analysis = dto.getAnalysis() != null ? new Analysis(dto.getAnalysis()) : null;
    }

    public LastSaleDto toAggregate() {
        return LastSaleDto.builder()
                .id(this.id)
                .documentType(documentType)
                .mls(mls)
                .publicRecord(publicRecord)
                .build();
    }

}
