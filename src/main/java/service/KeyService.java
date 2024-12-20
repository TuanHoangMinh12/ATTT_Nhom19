package service;

import java.security.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

public class KeyService {

    private KeyPair keyPair;

    // Hàm tạo cặp key
    public void generateKeyPair() throws NoSuchAlgorithmException {
        // Sử dụng RSA với độ dài key là 2048 bit
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

    // Lấy Private Key dưới dạng Base64
    public String getPrivateKey() {
        if (keyPair == null) return null;
        PrivateKey privateKey = keyPair.getPrivate();
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Lấy Public Key dưới dạng Base64
    public String getPublicKey() {
        if (keyPair == null) return null;
        PublicKey publicKey = keyPair.getPublic();
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    public void checkDatabaseConnection() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Kết nối database thành công!");
        } catch (SQLException e) {
            throw new SQLException("Không thể kết nối database.", e);
        }
    }

    public void savePublicKey(String publicKey, String email) {
        String sql = "UPDATE Users SET PublicKey = ? WHERE email = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, publicKey);
            preparedStatement.setString(2, email); // Dùng email để tìm người dùng cụ thể
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("PublicKey đã được lưu vào database cho người dùng!");
            } else {
                System.out.println("Không tìm thấy người dùng!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
