package service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

public class EmailService {
    private final String fromEmail = "phamhoangtien832003@gmail.com";
    private final String password = "gjsl utbv ptqs csrr"; //

    // Phương thức tạo mã OTP ngẫu nhiên
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);  // Tạo số ngẫu nhiên 6 chữ số
        return String.valueOf(otp);
    }

    // Phương thức gửi email với mã OTP
    public void sendOtpEmail(String toEmail, String otp) {
        String host = "smtp.gmail.com"; // Máy chủ SMTP của Gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");  // Cổng SMTP của Gmail
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Thiết lập Session với thông tin đăng nhập
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Tạo email
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Mã OTP Xác Thực");
            message.setText("Mã OTP của bạn là: " + otp);

            // Gửi email
            Transport.send(message);
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi gửi email.");
        }
    }
}
