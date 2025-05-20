package com.kynsoft.finamer.digitalsignature.infrastructure.service;

import com.kynsoft.finamer.digitalsignature.domain.dto.*;
import com.kynsoft.finamer.digitalsignature.domain.exception.InvalidSignaturePositionException;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureService;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureCertificateService;
import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.*;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.Base64;

/**
 * Servicio de firma y validación PDF usando PDFBox 3 + BouncyCastle.
 */
@Service
public class DigitalSignatureService implements IDigitalSignatureService, SignatureInterface {

    private static final Logger log = LoggerFactory.getLogger(DigitalSignatureService.class);

    /* ---------- propiedades inyectadas ---------- */
    @Value("${signature.keystore.path:classpath:keystore.p12}")
    private String keystorePath;

    @Value("${signature.keystore.password:changeit}")
    private String keystorePassword;

    @Value("${signature.keystore.default.alias:default}")
    private String defaultAlias;

    private final ResourceLoader resourceLoader;
    private final IDigitalSignatureCertificateService digitalSignatureCertificateService;

    /* ---------- certificados ---------- */
    private KeyStore      serverKeyStore;
    private PrivateKey    privateKey;
    private Certificate[] certificateChain;

    private KeyStore      tmpKeyStore;
    private PrivateKey    tmpPrivateKey;
    private Certificate[] tmpCertChain;

    static { Security.addProvider(new BouncyCastleProvider()); }

    public DigitalSignatureService(ResourceLoader resourceLoader, IDigitalSignatureCertificateService digitalSignatureCertificateService) {
        this.resourceLoader = resourceLoader;
        this.digitalSignatureCertificateService = digitalSignatureCertificateService;
        try { initServerKeyStore(); }
        catch (Exception e) { log.error("No se pudo cargar el keystore del servidor", e); }
    }

    /* =====================================================================
       CONVERSIONES BASE64 A BYTES
       ===================================================================== */
    private byte[] decodeBase64(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            return null;
        }
        
        try {
            // Eliminar prefijos de data URL si existen (por ejemplo, "data:application/pdf;base64,")
            String cleanBase64 = base64String;
            if (base64String.contains(",")) {
                cleanBase64 = base64String.split(",")[1];
            }
            
            return Base64.getDecoder().decode(cleanBase64);
        } catch (IllegalArgumentException e) {
            log.error("Error decodificando Base64: " + e.getMessage(), e);
            throw new RuntimeException("El formato Base64 proporcionado no es válido", e);
        }
    }

    /* =====================================================================
       KEYSTORE
       ===================================================================== */
    private void initServerKeyStore() throws Exception {
        if (keystorePath == null || keystorePath.isBlank()) return;
        Resource res = resourceLoader.getResource(keystorePath);
        if (!res.exists()) return;
        try (InputStream in = res.getInputStream()) {
            serverKeyStore = KeyStore.getInstance("PKCS12");
            serverKeyStore.load(in, keystorePassword.toCharArray());
        }
    }

    private void loadCertificateFromServer(String alias) throws Exception {
        if (serverKeyStore == null)
            throw new IllegalStateException("Keystore del servidor no disponible");
        if (alias == null || alias.isBlank()) alias = defaultAlias;
        if (!serverKeyStore.containsAlias(alias))
            throw new IllegalArgumentException("Alias inexistente: " + alias);

        privateKey       = (PrivateKey) serverKeyStore.getKey(alias, keystorePassword.toCharArray());
        certificateChain = serverKeyStore.getCertificateChain(alias);
    }

    private void loadCertificateFromRequest(byte[] p12Bytes, String password) throws Exception {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(p12Bytes)) {
            tmpKeyStore = KeyStore.getInstance("PKCS12");
            tmpKeyStore.load(bis, password.toCharArray());
            String alias = tmpKeyStore.aliases().nextElement();
            tmpPrivateKey = (PrivateKey) tmpKeyStore.getKey(alias, password.toCharArray());
            tmpCertChain  = tmpKeyStore.getCertificateChain(alias);

            privateKey       = tmpPrivateKey;
            certificateChain = tmpCertChain;
        }
    }

    /* =====================================================================
       CARGA DE CERTIFICADOS DESDE LA BASE DE DATOS
       ===================================================================== */
    /**
     * Carga un certificado P12 desde la base de datos usando su UUID
     * @param certificateId UUID del certificado a cargar
     * @throws Exception Si el certificado no se encuentra o está inactivo
     */
    private void loadCertificateFromDatabase(String certificateId) throws Exception {
        if (certificateId == null || certificateId.isBlank()) {
            throw new IllegalArgumentException("ID del certificado es obligatorio");
        }
        
        UUID uuid;
        try {
            uuid = UUID.fromString(certificateId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID del certificado no es un UUID válido"+e.getMessage());
        }
        
        // Buscar el certificado en la base de datos
        Optional<DigitalSignatureCertificate> certificateOpt = digitalSignatureCertificateService.findById(uuid);
        if (certificateOpt.isEmpty()) {
            throw new IllegalArgumentException("Certificado no encontrado con el ID: " + certificateId);
        }
        
        DigitalSignatureCertificate certificate = certificateOpt.get();
        
        // Verificar que el certificado esté activo
        if (!certificate.getIsActive()) {
            throw new IllegalArgumentException("El certificado con ID: " + certificateId + " no está activo");
        }
        
        // Verificar que no esté expirado
        if (certificate.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("El certificado con ID: " + certificateId + " ha expirado");
        }
        
        // Cargar el certificado P12 desde los bytes almacenados
        byte[] p12Bytes = certificate.getCertificateP12();
        String password = certificate.getCertificatePassword();
        
        // Usar el método existente para cargar el certificado
        loadCertificateFromRequest(p12Bytes, password);
    }
    
    /* =====================================================================
       FIRMA PDF
       ===================================================================== */
    @Override
    public SignResponseDto signDocument(SignRequestDto req) {

        try {
            // Convertir documento base64 a bytes si es necesario
            byte[] documentBytes = null;
            if (req.getDocument() != null) {
                // Siempre decodificar el documento base64
                documentBytes = decodeBase64(req.getDocument());
                if (documentBytes == null) {
                    throw new IllegalArgumentException("El documento base64 proporcionado no es válido");
                }
            } else {
                throw new IllegalArgumentException("Documento requerido");
            }
            
           if (req.getCertificateP12Id() != null && !req.getCertificateP12Id().isBlank()) {
                // Opción 2: Certificado por ID desde la base de datos
                loadCertificateFromDatabase(req.getCertificateP12Id());
            }
//           else {
//                // Opción 3: Certificado del servidor por alias
//                loadCertificateFromServer(req.getCertificateAlias());
//            }

            if (req.getVisibleSignature() != null)
                validateSignaturePosition(req.getVisibleSignature(), documentBytes);

            PDDocument doc = Loader.loadPDF(documentBytes);

            PDSignature sig = new PDSignature();
            sig.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
            sig.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);

            X509Certificate cert = (X509Certificate) certificateChain[0];
            sig.setName(cert.getSubjectX500Principal().getName());
            sig.setReason(Optional.ofNullable(req.getReason()).orElse("Firma digital"));
            sig.setLocation(Optional.ofNullable(req.getLocation()).orElse("No especificada"));
            sig.setSignDate(Calendar.getInstance());

            // Usamos firma invisible por defecto
            SignatureOptions opts = new SignatureOptions();
            
            // Solo intentamos crear la firma visible si se solicita explícitamente
            if (req.getVisibleSignature() != null) {
                try {
                    // Intentamos con la firma visible simple
                    opts = createSimpleVisibleSignature(doc, req.getVisibleSignature());
                } catch (Exception e) {
                    // Si falla la firma visible, usamos invisible
                    log.warn("No se pudo crear firma visible, usando invisible: " + e.getMessage());
                    opts = new SignatureOptions();
                }
            }

            doc.addSignature(sig, this, opts);

            ByteArrayOutputStream signedOut = new ByteArrayOutputStream();
            doc.saveIncremental(signedOut);
            doc.close();
            opts.close();
            
            byte[] signedBytes = signedOut.toByteArray();
            
            // Guardar el documento firmado en la carpeta de recursos
//            String savedPath = null;
//            if (req.getDocumentName() != null && !req.getDocumentName().isBlank()) {
//                savedPath = saveSignedDocumentToResources(signedBytes, req.getDocumentName());
//            }

            return SignResponseDto.builder()
                    .signedDocument(signedBytes)
                    .signaturePosition(req.getVisibleSignature())
                    .signerName(sig.getName())
                    .signatureDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
                    .filePath("savedPath") // Añadimos la ruta donde se guardó el documento
                    .build();

        } catch (Exception e) {
            log.error("Error firmando documento", e);
            throw new RuntimeException("Error firmando documento: " + e.getMessage(), e);
        } finally {
            tmpKeyStore = null; tmpPrivateKey = null; tmpCertChain = null;
        }
    }

    /* -------------- construcción de firma visible simplificada -------------- */
    private SignatureOptions createSimpleVisibleSignature(PDDocument doc, VisibleSignatureDto vs) throws IOException {
        SignatureOptions signatureOptions = new SignatureOptions();
        signatureOptions.setPage(vs.getPage() - 1);

        // Configuramos la posición y la página - Ajustamos el ancho y alto según la imagen
        float width = Optional.ofNullable(vs.getWidth()).orElse(300f);
        float height = Optional.ofNullable(vs.getHeight()).orElse(80f);
        PDRectangle rect = new PDRectangle(vs.getX(), vs.getY(), width, height);

        PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
        if (acroForm == null) {
            acroForm = new PDAcroForm(doc);
            doc.getDocumentCatalog().setAcroForm(acroForm);
        }

        PDSignatureField signatureField = new PDSignatureField(acroForm);
        signatureField.getWidgets().get(0).setRectangle(rect);
        signatureField.getWidgets().get(0).setPage(doc.getPage(vs.getPage() - 1));

        // Añadir apariencia visual al campo de firma
        PDAppearanceStream appearanceStream = new PDAppearanceStream(doc);
        appearanceStream.setResources(new PDResources());
        appearanceStream.setBBox(new PDRectangle(width, height));

        try (PDPageContentStream cs = new PDPageContentStream(doc, appearanceStream)) {
            // Eliminamos el borde para que sea exactamente como en la imagen
            // Ya no dibujamos el borde del rectángulo

            // Obtenemos datos para el QR y la firma
            X509Certificate cert = (X509Certificate) certificateChain[0];
            String signerName = cert.getSubjectX500Principal().getName();
            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

            // Extraemos el CN del nombre del certificado si es posible
            String displayName = signerName;
            if (signerName.contains("CN=")) {
                String[] parts = signerName.split("CN=");
                if (parts.length > 1) {
                    String cn = parts[1];
                    int endIndex = cn.indexOf(",");
                    if (endIndex > 0) {
                        displayName = cn.substring(0, endIndex);
                    } else {
                        displayName = cn;
                    }
                }
            }

            // Generamos contenido reducido para el QR (solo lo esencial para escaneo)
            String qrContent = "Firmado por: " + displayName + "\n" +
                              "Fecha: " + currentDate;

            // Calculamos tamaño y posición del código QR - Aumentamos un poco el tamaño
            float qrSize = Math.min(height - 5, height * 0.96f); // Aumentado de 0.85f a 0.92f
            // Margen suficiente desde el borde izquierdo
            float qrX = 5; // Reducido de 8 a 5 para dar más espacio al QR
            // Centramos verticalmente el QR
            float qrY = (height - qrSize) / 2;

            // Generamos el QR con dimensiones adecuadas y suficiente margen
            int generationSize = 240; // Aumentado de 200 a 220 píxeles
            BufferedImage qrImage = generateQRCode(qrContent, generationSize);

            // Convertimos la imagen del QR a un objeto PDImageXObject
            PDImageXObject qrPdImage = LosslessFactory.createFromImage(doc, qrImage);

            // Dibujamos la imagen del QR completa en el espacio disponible
            cs.drawImage(qrPdImage, qrX, qrY, qrSize, qrSize);

            // Añadimos texto para la firma según formato de la imagen
            cs.beginText();
            PDFont fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDFont fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            
            // Aumentamos la distancia entre el QR y el texto
            float textStartX = qrX + qrSize-4;
            
            // Texto "Firmado electrónicamente por:" exactamente como en la imagen
            cs.setFont(fontRegular, 8);
            cs.setNonStrokingColor(0, 0, 0); // Color negro
            cs.newLineAtOffset(textStartX, height - 17);//15
            cs.showText("Firmado electrónicamente por:");
            
            // Nombre del firmante en negrita pero con tamaño de letra reducido
            cs.setFont(fontBold, 8); // Tamaño de letra reducido
            cs.newLineAtOffset(0, -12); // Reducido de -4 a -1 para acercar los textos
            
            // Limitamos el tamaño del nombre para evitar que se salga del área
            if (displayName.length() > 30) {
                displayName = displayName.substring(0, 30) + "...";
            }
            
            // Convertimos el nombre a mayúsculas según la imagen
            String displayNameUpper = displayName.toUpperCase();
            
            // Si el nombre es muy largo, dividirlo en dos líneas
            if (displayName.length() > 15 && displayName.contains(" ")) {
                String[] nameParts = displayName.split(" ");
                if (nameParts.length > 2) {
                    // Primera línea: primeros dos elementos
                    String firstName = nameParts[0] + " " + nameParts[1];
                    cs.showText(firstName.toUpperCase());
                    
                    // Segunda línea: resto del nombre
                    StringBuilder lastName = new StringBuilder();
                    for (int i = 2; i < nameParts.length; i++) {
                        lastName.append(nameParts[i]).append(" ");
                    }
                    cs.newLineAtOffset(0, -12);
                    cs.showText(lastName.toString().trim().toUpperCase());
                } else {
                    // Si solo hay dos partes, mostrar todo en una línea
                    cs.showText(displayNameUpper);
                }
            } else {
                // Si el nombre es corto, mostrarlo en una sola línea
                cs.showText(displayNameUpper);
            }
            
            cs.endText();
        }

        PDAppearanceDictionary appearance = new PDAppearanceDictionary();
        appearance.setNormalAppearance(appearanceStream);
        signatureField.getWidgets().get(0).setAppearance(appearance);

        // Configurar la apariencia
        signatureField.getWidgets().get(0).setRectangle(rect);
        signatureField.getWidgets().get(0).setPage(doc.getPage(vs.getPage() - 1));

        doc.getPage(vs.getPage() - 1).getAnnotations().add(signatureField.getWidgets().get(0));
        acroForm.getFields().add(signatureField);

        return signatureOptions;
    }

    /**
     * Genera un código QR como imagen BufferedImage
     * @param content Contenido del código QR
     * @param size Tamaño en píxeles
     * @return BufferedImage con el código QR
     */
    private BufferedImage generateQRCode(String content, int size) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // Aumentamos el margen para asegurar que el QR se vea completo
            hints.put(EncodeHintType.MARGIN, 4); // Mayor margen para mejor visibilidad
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            
            // Ajustamos el tamaño de la matriz del QR para dejar espacio para los márgenes
            int matrixSize = size - 16; // Reducimos el tamaño para dejar espacio al margen
            if (matrixSize <= 0) matrixSize = size - 8;
            
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                content, BarcodeFormat.QR_CODE, matrixSize, matrixSize, hints);
            
            // Creamos una imagen más grande con fondo blanco para agregar margen manualmente
            BufferedImage qrImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = qrImage.createGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, size, size);
            graphics.dispose();
            
            // Copiamos la matriz del QR al centro de la imagen más grande
            int startPos = (size - matrixSize) / 2;
            for (int x = 0; x < matrixSize; x++) {
                for (int y = 0; y < matrixSize; y++) {
                    qrImage.setRGB(x + startPos, y + startPos, 
                        bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
            
            return qrImage;
        } catch (Exception e) {
            log.error("Error generando código QR", e);
            // Si falla, retornamos una imagen en blanco
            BufferedImage emptyImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = emptyImage.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, size, size);
            g.dispose();
            return emptyImage;
        }
    }

    /* =====================================================================
       VALIDACIÓN DE POSICIÓN
       ===================================================================== */
    @Override
    public void validateSignaturePosition(VisibleSignatureDto vs, byte[] pdf) {
        try (PDDocument doc = Loader.loadPDF(pdf)) {
            int page = vs.getPage();
            if (page < 1 || page > doc.getNumberOfPages())
                throw new InvalidSignaturePositionException("Número de página inválido");

            PDRectangle mbox = doc.getPage(page - 1).getMediaBox();
            float w = Optional.ofNullable(vs.getWidth()).orElse(200f);
            float h = Optional.ofNullable(vs.getHeight()).orElse(50f);

            if (vs.getX() < 0 || vs.getY() < 0 ||
                (vs.getX() + w) > mbox.getWidth() ||
                (vs.getY() + h) > mbox.getHeight())
                throw new InvalidSignaturePositionException("Posición fuera de la página");
        } catch (IOException e) {
            throw new RuntimeException("No se pudo validar la posición", e);
        }
    }

    /* =====================================================================
       IMPLEMENTACIÓN SignatureInterface (CMS detached)
       ===================================================================== */
    @Override
    public byte[] sign(InputStream content) throws IOException {
        try {
            if (privateKey == null || certificateChain == null || certificateChain.length == 0)
                throw new IOException("No hay certificado cargado");

            X509Certificate cert = (X509Certificate) certificateChain[0];

            CMSSignedDataGenerator gen   = new CMSSignedDataGenerator();
            JcaCertStore store           = new JcaCertStore(Arrays.asList(certificateChain));

            ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .build(privateKey);

            X509CertificateHolder holder = new JcaX509CertificateHolder(cert);

            /* atributo signing‑time */
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.add(new Attribute(CMSAttributes.signingTime,
                    new DERSet(new Time(new Date()))));

            AttributeTable signed = new AttributeTable(v);

            JcaSignerInfoGeneratorBuilder b =
                    new JcaSignerInfoGeneratorBuilder(
                            new JcaDigestCalculatorProviderBuilder()
                                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                                    .build());
            b.setSignedAttributeGenerator(new DefaultSignedAttributeTableGenerator(signed));

            gen.addSignerInfoGenerator(b.build(signer, holder));
            gen.addCertificates(store);

            byte[] data = IOUtils.toByteArray(content);

            CMSSignedData cms = gen.generate(new CMSProcessableByteArray(data), false);
            return cms.getEncoded();

        } catch (Exception e) {
            log.error("Error generando firma CMS", e);
            throw new IOException("Error generando firma CMS: " + e.getMessage(), e);
        }
    }

    /* =====================================================================
       VALIDAR FIRMAS DE UN PDF
       ===================================================================== */
    @Override
    public ValidationResponseDto validateDocument(ValidationRequestDto req) {
        try {
            byte[] documentBytes = req.getDocument();
            
            // Verificamos que exista el documento
            if (documentBytes == null || documentBytes.length == 0) {
                throw new IllegalArgumentException("Documento requerido");
            }

            try (PDDocument doc = Loader.loadPDF(documentBytes)) {
                List<PDSignature> sigs = doc.getSignatureDictionaries();
                List<SignatureInfoDto> infoList = new ArrayList<>();
                boolean allValid = true;

                for (PDSignature sig : sigs) {
                    byte[] sigBytes   = sig.getContents(documentBytes);
                    byte[] signedPart = sig.getSignedContent(documentBytes);

                    CMSSignedData cms = new CMSSignedData(
                            new CMSProcessableByteArray(signedPart), sigBytes);

                    boolean valid = cms.getSignerInfos().getSigners().size() > 0;
                    if (!valid) allValid = false;

                    PDRectangle r = locateRect(doc, sig);
                    VisibleSignatureDto pos = null;
                    boolean visible = r != null;
                    if (visible) {
                        int page = locatePage(doc, sig) + 1;
                        pos = VisibleSignatureDto.builder()
                                .page(page)
                                .x(r.getLowerLeftX())
                                .y(r.getLowerLeftY())
                                .width(r.getWidth())
                                .height(r.getHeight())
                                .build();
                    }

                    infoList.add(SignatureInfoDto.builder()
                            .signerName(sig.getName())
                            .signatureDate(sig.getSignDate() == null ? null :
                                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                            .format(sig.getSignDate().getTime()))
                            .visible(visible)
                            .position(pos)
                            .valid(valid)
                            .build());
                }

                return ValidationResponseDto.builder()
                        .valid(allValid)
                        .signatures(infoList)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error validando documento", e);
            throw new RuntimeException("Error validando documento: " + e.getMessage(), e);
        }
    }

    /* ---------------------- utilidades ---------------------- */
    private PDRectangle locateRect(PDDocument doc, PDSignature sig) {
        try {
            PDAcroForm acro = doc.getDocumentCatalog().getAcroForm();
            if (acro != null)
                for (var f : acro.getFields())
                    if (f instanceof PDSignatureField sf && sig.equals(sf.getSignature()))
                        return sf.getWidgets().get(0).getRectangle();
        } catch (Exception ignored) {}
        return null;
    }

    private int locatePage(PDDocument doc, PDSignature sig) {
        try {
            PDAcroForm acro = doc.getDocumentCatalog().getAcroForm();
            if (acro != null)
                for (var f : acro.getFields())
                    if (f instanceof PDSignatureField sf && sig.equals(sf.getSignature())) {
                        PDPage p = sf.getWidgets().get(0).getPage();
                        for (int i = 0; i < doc.getNumberOfPages(); i++)
                            if (doc.getPage(i) == p) return i;
                    }
        } catch (Exception ignored) {}
        return 0;
    }

    /**
     * Guarda el documento PDF firmado en la carpeta de recursos
     * @param signedDocument bytes del documento firmado
     * @param documentName nombre del documento a guardar
     * @return ruta del archivo guardado
     */
    private String saveSignedDocumentToResources(byte[] signedDocument, String documentName) {
        try {
            // Obtenemos la ruta de recursos
            String resourcePath = "src/main/resources/signed-documents";
            File resourceDir = new File(resourcePath);
            
            // Creamos el directorio si no existe
            if (!resourceDir.exists() && !resourceDir.mkdirs()) {
                log.warn("No se pudo crear el directorio para documentos firmados: {}", resourcePath);
                return null;
            }
            
            // Generamos un nombre de archivo seguro
            String safeFileName = documentName != null ? 
                    documentName.replaceAll("[^a-zA-Z0-9_\\-\\.]", "_") : 
                    "documento_firmado_" + System.currentTimeMillis();
            
            // Aseguramos que tenga extensión .pdf
            if (!safeFileName.toLowerCase().endsWith(".pdf")) {
                safeFileName += ".pdf";
            }
            
            // Ruta completa del archivo
            Path filePath = Paths.get(resourcePath, safeFileName);
            
            // Guardamos el archivo
            Files.write(filePath, signedDocument);
            log.info("Documento firmado guardado en: {}", filePath);
            
            return filePath.toString();
        } catch (Exception e) {
            log.error("Error al guardar documento firmado: {}", e.getMessage(), e);
            return null;
        }
    }
}