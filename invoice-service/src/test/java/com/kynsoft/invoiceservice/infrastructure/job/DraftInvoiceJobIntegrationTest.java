package com.kynsoft.invoiceservice.infrastructure.job;

import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.infrastructure.entities.*;
import com.kynsoft.invoiceservice.infrastructure.mapper.MapperInvoice;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceRepository;
import com.kynsoft.invoiceservice.domain.service.impl.InvoiceService;
import com.kynsoft.invoiceservice.infrastructure.util.CredentialUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import ec.e.facturacion.sri.modelo.Factura;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DraftInvoiceJobIntegrationTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private IInvoiceIssuerService invoiceIssuerService;

    @Mock
    private CredentialUtil credentialUtil;

    @Mock
    private MapperInvoice mapperInvoice;

    @InjectMocks
    private DraftInvoiceJob draftInvoiceJob;

    @Captor
    private ArgumentCaptor<UUID> invoiceIdCaptor;
    
    @Captor
    private ArgumentCaptor<UUID> userIdCaptor;

    private List<Invoice> mockInvoices;
    private ProcessInvoice mockProcessInvoice;

    @BeforeEach
    void setUp() {
        // Configurar invoice de prueba
        Invoice mockInvoice = new Invoice();
        mockInvoice.setId(UUID.randomUUID());
        mockInvoice.setStatus(InvoiceStatus.DRAFT);
        
        Issuer mockIssuer = new Issuer();
        mockIssuer.setId(UUID.randomUUID());
        mockInvoice.setIssuer(mockIssuer);
        
        mockInvoices = List.of(mockInvoice);
        
        // Configurar ProcessInvoice mock
        Factura mockFactura = mock(Factura.class);
        InputStream mockStream = new ByteArrayInputStream("test".getBytes());
        mockProcessInvoice = new ProcessInvoice(mockInvoice.getId(), mockFactura, mockStream, "password");
    }

    @Test
    void buscarFacturasEnBorrador_shouldProcessDraftInvoices() {
        // Arrange
        when(invoiceRepository.findByStatus(InvoiceStatus.DRAFT)).thenReturn(mockInvoices);
        when(mapperInvoice.convertToFactura(any(Invoice.class))).thenReturn(mockProcessInvoice);
        
        // Usar un método spy para evitar ejecutar realmente generateDocumentsAsync
        DraftInvoiceJob spyJob = spy(draftInvoiceJob);
        doNothing().when(spyJob).generateDocumentsAsync(
                any(Factura.class), 
                invoiceIdCaptor.capture(),
                userIdCaptor.capture(),
                any(InputStream.class), 
                anyString());
        
        // Act
        spyJob.buscarFacturasEnBorrador();
        
        // Assert
        verify(invoiceRepository).findByStatus(InvoiceStatus.DRAFT);
        verify(mapperInvoice).convertToFactura(mockInvoices.get(0));
        verify(spyJob).generateDocumentsAsync(
                eq(mockProcessInvoice.getFactura()), 
                eq(mockProcessInvoice.getInvoiceId()),
                any(UUID.class),
                eq(mockProcessInvoice.getP12Bytes()), 
                eq(mockProcessInvoice.getP12Password()));
        
        // Verificar que el ID de la factura se pasa correctamente
        assertEquals(mockInvoices.get(0).getId(), invoiceIdCaptor.getValue());
    }
    
    @Test
    void buscarFacturasEnBorrador_shouldHandleEmptyInvoiceList() {
        // Arrange
        when(invoiceRepository.findByStatus(InvoiceStatus.DRAFT)).thenReturn(Collections.emptyList());
        
        // Act
        draftInvoiceJob.buscarFacturasEnBorrador();
        
        // Assert
        verify(invoiceRepository).findByStatus(InvoiceStatus.DRAFT);
        verify(mapperInvoice, never()).convertToFactura(any());
    }
}
