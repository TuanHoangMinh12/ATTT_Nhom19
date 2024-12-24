package view;

import service.KeyService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class KeyManagementApp extends JFrame {
    private JTextArea txtPrivateKey;
    private JTextArea txtPublicKey;
    private JButton btnGenerateKey;
    private JButton btnSavePrivateKey;
    private JButton btnReportLostKey;
    private JLabel lblMessage;
    private String userEmail;  // Biến lưu email người dùng

    // Khởi tạo KeyService
    private KeyService keyService;

    public KeyManagementApp(String email) {
        // Khởi tạo KeyService
        keyService = new KeyService();
        this.userEmail = email; // Lưu email vào biến userEmail

        // Thiết lập cửa sổ
        setTitle("Quản Lý Key");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout chính
        setLayout(new BorderLayout());

        // Panel trên cùng: các nút chức năng
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        btnGenerateKey = new JButton("Tạo Cặp Key");
        btnSavePrivateKey = new JButton("Lưu Key");
        btnReportLostKey = new JButton("Báo Mất Key");
        topPanel.add(btnGenerateKey);
        topPanel.add(btnSavePrivateKey);
        topPanel.add(btnReportLostKey);

        // Panel giữa: hiển thị private và public key
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        txtPrivateKey = new JTextArea(10, 30);
        txtPublicKey = new JTextArea(10, 30);
        txtPrivateKey.setBorder(BorderFactory.createTitledBorder("Private Key"));
        txtPublicKey.setBorder(BorderFactory.createTitledBorder("Public Key"));
        txtPrivateKey.setLineWrap(true);
        txtPrivateKey.setWrapStyleWord(true);
        txtPublicKey.setLineWrap(true);
        txtPublicKey.setWrapStyleWord(true);
        centerPanel.add(new JScrollPane(txtPrivateKey));
        centerPanel.add(new JScrollPane(txtPublicKey));

        // Panel dưới cùng: thông báo
        JPanel bottomPanel = new JPanel();
        lblMessage = new JLabel("");
        bottomPanel.add(lblMessage);

        // Thêm các panel vào cửa sổ
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Kiểm tra kết nối database
        checkDatabaseConnection();

        // Thêm sự kiện cho các nút
        btnGenerateKey.addActionListener(e -> generateKey());
        btnSavePrivateKey.addActionListener(e -> savePrivateKey());
        btnReportLostKey.addActionListener(e -> reportLostKey());
    }

    private void checkDatabaseConnection() {
        try {
            keyService.checkDatabaseConnection();
            lblMessage.setText("Kết nối database thành công!");
            lblMessage.setForeground(Color.GREEN);
        } catch (Exception ex) {
            lblMessage.setText("Kết nối database thất bại!");
            lblMessage.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }

    private void generateKey() {
        try {
            // Gọi KeyService để tạo cặp key
            keyService.generateKeyPair();

            // Lấy private key và public key từ KeyService
            String privateKey = keyService.getPrivateKey();
            String publicKey = keyService.getPublicKey();

            // Hiển thị trên giao diện
            txtPrivateKey.setText(privateKey);
            txtPublicKey.setText(publicKey);

            lblMessage.setText("Tạo cặp key thành công!");
            lblMessage.setForeground(Color.GREEN);
        } catch (Exception ex) {
            lblMessage.setText("Lỗi khi tạo cặp key.");
            lblMessage.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }

    private void savePrivateKey() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu Private Key");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Lưu Private Key vào file
                writer.write(txtPrivateKey.getText());

                // Lưu Public Key vào database
                String publicKey = txtPublicKey.getText();
                if (!publicKey.isEmpty()) {
                    keyService.savePublicKey(publicKey, userEmail);  // Lưu publicKey vào DB cho người dùng theo email
                    lblMessage.setText("Private key đã được lưu về máy và public key đã được lưu vào database!");
                    lblMessage.setForeground(Color.GREEN);
                } else {
                    lblMessage.setText("Public key trống, không thể lưu vào database.");
                    lblMessage.setForeground(Color.RED);
                }
            } catch (IOException ex) {
                lblMessage.setText("Lỗi khi lưu private key.");
                lblMessage.setForeground(Color.RED);
                ex.printStackTrace();
            }
        }
    }

    private void reportLostKey() {
        try {
            if (userEmail == null || userEmail.isEmpty()) {
                lblMessage.setText("Không có email người dùng để báo mất key.");
                lblMessage.setForeground(Color.RED);
                return;
            }

            // Báo mất key và tạo cặp khóa mới qua KeyService
            keyService.reportLostKeyAndGenerateNewKey(userEmail);

            // Cập nhật giao diện hiển thị khóa mới
            String newPrivateKey = keyService.getPrivateKey();
            String newPublicKey = keyService.getPublicKey();
            txtPrivateKey.setText(newPrivateKey);
            txtPublicKey.setText(newPublicKey);

            lblMessage.setText("Đã báo mất key và tạo cặp key mới thành công!");
            lblMessage.setForeground(Color.GREEN);
        } catch (Exception ex) {
            lblMessage.setText("Lỗi khi báo mất key và tạo key mới.");
            lblMessage.setForeground(Color.RED);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Kiểm tra kết nối và bắt đầu ứng dụng
        SwingUtilities.invokeLater(() -> {
            String email = "user@example.com";  // Ví dụ email
            KeyManagementApp app = new KeyManagementApp(email);  // Truyền email vào constructor
            app.setVisible(true);
        });
    }
}