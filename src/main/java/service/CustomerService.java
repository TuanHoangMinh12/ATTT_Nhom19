package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService {
    public Integer findUserIdByEmail(String email) {
        String query = "SELECT id_User FROM customer WHERE email = ?"; // Replace "customers" with your actual table name
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_User"); // Return only the idUser
                }
            }

        } catch ( SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no user is found
    }


        public static void main(String[] args) {
            CustomerService customerService = new CustomerService();
            Integer userId = customerService.findUserIdByEmail("huynhduythuan668@gmail.com");

            if (userId != null) {
                System.out.println("User ID found: " + userId);
            } else {
                System.out.println("No user found with the given email.");

        }
    }
}
