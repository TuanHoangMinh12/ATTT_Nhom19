package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/web_ban_sach";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver không tìm thấy!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Kết nối cơ sở dữ liệu thành công!");
            } else {
                System.out.println("Kết nối cơ sở dữ liệu thất bại!");
            }
        } catch (SQLException e) {
            System.out.println("Đã xảy ra lỗi khi kết nối cơ sở dữ liệu:");
            e.printStackTrace();
        }
    }
}
