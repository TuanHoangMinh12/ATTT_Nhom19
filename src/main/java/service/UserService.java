package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {

    private Connection connect() throws SQLException {
        // Thay đổi thông tin kết nối của bạn
        String url = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";
        return DriverManager.getConnection(url, username, password);
    }

    public void savePublicKey(String email, String publicKey) {
        // Câu lệnh SQL để cập nhật publicKey cho người dùng theo email
        String query = "UPDATE users SET PublicKey = ? WHERE email = ?";

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Thiết lập các tham số vào câu lệnh SQL
            statement.setString(1, publicKey);  // PublicKey
            statement.setString(2, email);       // email

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("PublicKey đã được lưu thành công!");
            } else {
                System.out.println("Không tìm thấy người dùng với email này.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi lưu publicKey.");
        }
    }
}
