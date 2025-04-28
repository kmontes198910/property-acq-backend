package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.EmbeddableAddressDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.FeaturesDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.OwnerDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.SaleDto;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PropertyDto {

    private UUID id;
    private EmbeddableAddressDto address;
    private PropertyType propertyType;
    private Status status;
    private Boolean distressed;
    private Boolean shortSale;
    private Boolean hoa;
    private Boolean ownerOccupied;
    private String occupancy;
    private Integer squareFeet;
    private Integer lotSize;
    private Integer yearBuilt;
    private int bedrooms;
    private int bathrooms;
    private String lengthOfOwnership;
    private String purchaseMethod;
    private String apn;
    private String description;
    private Double estimatedValue;
    private Double lastRecordedValue;
    private Double lastSalePrice;
    private LocalDate lastSaleDate;
    private String lastDocumentType;
    private BigDecimal equity;
    private BigDecimal monthlyRentEstimate;
    private Double grossYield;
    private OwnerDto owner;
    private FeaturesDto features;
    private SaleDto sale;
    private List<TaxAssessmentsDto> taxAssessments;
    private List<MortgageDto> mortgages;
    private List<HistoryDto> history;
    private List<LienDto> liens;
}
