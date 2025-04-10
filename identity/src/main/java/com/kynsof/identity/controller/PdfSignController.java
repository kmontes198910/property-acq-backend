package com.kynsof.identity.controller;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

@RestController
@RequestMapping("/api/pdf-sign")
public class PdfSignController {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @PostMapping(value = "/sign", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<byte[]>> signPdf(
            @RequestPart("pdf") MultipartFile pdf,
            @RequestPart("p12") MultipartFile p12,
            @RequestParam("password") String password,
            @RequestParam("page") int page,
            @RequestParam("x") float x,
            @RequestParam("y") float y,
            @RequestParam("width") float width,
            @RequestParam("height") float height
    ) {
        return Mono.fromCallable(() -> {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(p12.getInputStream(), password.toCharArray());

            String alias = keystore.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, password.toCharArray());
            Certificate[] chain = keystore.getCertificateChain(alias);

            ByteArrayOutputStream signedPdfOutput = new ByteArrayOutputStream();
            PdfReader reader = new PdfReader(pdf.getInputStream());
            PdfSigner signer = new PdfSigner(reader, signedPdfOutput, new StampingProperties());

            // Apariencia de la firma
            Rectangle rect = new Rectangle(x, y, width, height);
            PdfSignatureAppearance appearance = signer.getSignatureAppearance()
                    .setPageRect(rect)
                    .setPageNumber(page)
                    .setLocation("Firma digital")
                    .setReason("Autenticación")
                    .setReuseAppearance(false);
            signer.setFieldName("firma_digital");

            IExternalSignature pks = new PrivateKeySignature(privateKey, DigestAlgorithms.SHA256, "BC");
            IExternalDigest digest = new BouncyCastleDigest();

            signer.signDetached(digest, pks, chain, null, null, null, 0, PdfSigner.CryptoStandard.CADES);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documento-firmado.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(signedPdfOutput.toByteArray());
        });
    }
}