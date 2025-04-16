package com.kynsof.identity.controller;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

@Service
public class ServicioFirmaDigital {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public void firmarPDF(String rutaP12, String password, String pdfEntrada,
                          String pdfSalida, RectanguloFirma rectangulo)
            throws Exception {

        // Cargar el certificado P12
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(rutaP12), password.toCharArray());

        // Obtener la clave privada
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, password.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);

        // Preparar el PDF para firmar
        PdfReader reader = new PdfReader(pdfEntrada);
        PdfSigner signer = new PdfSigner(reader,
                new FileOutputStream(pdfSalida),
                new StampingProperties());

        // Configurar la apariencia de la firma
        Rectangle rect = new Rectangle(
                rectangulo.getX(),
                rectangulo.getY(),
                rectangulo.getWidth(),
                rectangulo.getHeight()
        );

        PdfSignatureAppearance appearance = signer.getSignatureAppearance()
                .setReason("Firma Digital")
                .setLocation("Ecuador")
                .setPageRect(rect)
                .setPageNumber(rectangulo.getPagina());

        signer.setFieldName("Firma");

        // Configurar la firma
        IExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256,
                BouncyCastleProvider.PROVIDER_NAME);
        IExternalDigest digest = new BouncyCastleDigest();

        // Firmar el documento
        signer.signDetached(digest, pks, chain, null, null, null, 0,
                PdfSigner.CryptoStandard.CMS);
    }
}