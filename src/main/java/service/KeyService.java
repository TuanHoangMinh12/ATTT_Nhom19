package service;

import java.security.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String selectIdQuery = "SELECT id_user FROM customer WHERE email = ?";
        String insertQuery = "INSERT INTO public_key (id_user, public_key, create_date, expire, status) VALUES (?, ?, NOW(), NULL, 1)";
        String expireOldKeysQuery = "UPDATE public_key SET expire = NOW(), status = 0 WHERE id_user = ? AND status = 1";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement selectIdStmt = connection.prepareStatement(selectIdQuery)) {

            // Bước 1: Lấy id_user từ email
            selectIdStmt.setString(1, email);
            try (ResultSet resultSet = selectIdStmt.executeQuery()) {
                if (resultSet.next()) {
                    int idUser = resultSet.getInt("id_user");

                    // Bước 2: Cập nhật trạng thái các public_key cũ
                    try (PreparedStatement expireStmt = connection.prepareStatement(expireOldKeysQuery)) {
                        expireStmt.setInt(1, idUser);
                        expireStmt.executeUpdate();
                    }

                    // Bước 3: Thêm mới public_key
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, idUser);
                        insertStmt.setString(2, publicKey);
                        insertStmt.executeUpdate();
                        System.out.println("PublicKey mới đã được thêm cho người dùng.");
                    }
                } else {
                    System.out.println("Không tìm thấy người dùng với email: " + email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
