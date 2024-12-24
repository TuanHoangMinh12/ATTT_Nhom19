package view;

import service.CustomerService;
import service.EmailService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthenticationApp extends JFrame {
    private JTextField txtEmail;
    private JTextField txtOtp;
    private JButton btnSendOtp;
    private JButton btnVerifyOtp;
    private JLabel lblMessage;
    private String generatedOtp = ""; // Biến lưu mã OTP đã gửi
    private String userEmail = ""; // Biến lưu email người dùng sau khi xác thực
    CustomerService customerService = new CustomerService();
    public AuthenticationApp() {
        // Thiết lập cửa sổ
        setTitle("Xác thực OTP");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);  // Dùng layout null để dễ dàng điều chỉnh vị trí

        // Khởi tạo các thành phần giao diện
        txtEmail = new JTextField();
        txtEmail.setBounds(50, 50, 300, 30);  // Vị trí và kích thước của email input
        add(txtEmail);

        btnSendOtp = new JButton("Gửi mã OTP");
        btnSendOtp.setBounds(50, 90, 300, 30);  // Vị trí và kích thước của nút gửi OTP
        add(btnSendOtp);

        txtOtp = new JTextField();
        txtOtp.setBounds(50, 130, 300, 30);  // Vị trí và kích thước của OTP input
        add(txtOtp);

        btnVerifyOtp = new JButton("Xác nhận OTP");
        btnVerifyOtp.setBounds(50, 170, 300, 30);  // Vị trí và kích thước của nút xác nhận OTP
        add(btnVerifyOtp);

        lblMessage = new JLabel("");
        lblMessage.setBounds(50, 210, 300, 30);  // Vị trí và kích thước của label thông báo
        add(lblMessage);

        // Xử lý sự kiện gửi OTP
        btnSendOtp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = txtEmail.getText();
                if (email.isEmpty()) {
                    lblMessage.setText("Vui lòng nhập email.");
                } else {
                    // Gửi mã OTP đến email
                    EmailService emailService = new EmailService();
                    generatedOtp = emailService.generateOtp(); // Tạo mã OTP
                    emailService.sendOtpEmail(email, generatedOtp); // Gửi OTP qua email

                    lblMessage.setText("Mã OTP đã được gửi tới email: " + email);
                    lblMessage.setForeground(Color.GREEN);
                    userEmail = email; // Lưu email vào biến toàn cục
                }
            }
        });

        // Xử lý sự kiện xác nhận OTP
        btnVerifyOtp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String otp = txtOtp.getText();
                if (otp.isEmpty()) {
                    lblMessage.setText("Vui lòng nhập mã OTP.");
                } else if (otp.equals(generatedOtp)) {
                    // Kiểm tra OTP nhập vào có khớp với OTP đã gửi
                    lblMessage.setText("Xác nhận thành công!");
                    lblMessage.setForeground(Color.GREEN);

                int id=    customerService.findUserIdByEmail(userEmail);
                if(id!=0){
                  FileSignerApp fileSignerApp = new FileSignerApp(userEmail); // Truyền email vào constructor
                    fileSignerApp.setVisible(true);
                    dispose();  // Đóng cửa sổ xác thực sau khi xác nhận thành công
                }
                    // Chuyển sang KeyManagementApp và truyền email vào đó

                } else {
                    lblMessage.setText("Mã OTP không chính xác.");
                    lblMessage.setForeground(Color.RED);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AuthenticationApp app = new AuthenticationApp();
                app.setVisible(true);
            }
        });
    }
}
