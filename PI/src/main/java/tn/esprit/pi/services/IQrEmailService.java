package tn.esprit.pi.services;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;
@Service
public class IQrEmailService {

    public boolean sendEmailWithQRCode(String subject, String message, String to, String qrCodeDetails) {
        boolean isSent = false;
        String from = "raniabenabdallah14@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("raniabenabdallah14@gmail.com", "qlhy siui bnpw unsr");
            }
        });

        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Create the text part
            BodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(message);
            multipart.addBodyPart(textBodyPart);

            // Generate QR code
            byte[] qrCodeBytes = generateQRCode(qrCodeDetails);

            // Create the attachment part
            BodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(qrCodeBytes, "image/png")));
            attachmentBodyPart.setFileName("qr-code.png");
            multipart.addBodyPart(attachmentBodyPart);

            // Set the multipart as the message's content
            mimeMessage.setContent(multipart);

            // Send email
            Transport.send(mimeMessage);
            System.out.println("Sent success. .");
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSent;
    }
    private byte[] generateQRCode(String qrCodeDetails) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeDetails, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}