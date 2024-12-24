package service;

import model.Infomationdelivers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InfomationDeliversService {

    public boolean addInfomationDelivers(Infomationdelivers info) {
        String query = "INSERT INTO infomationdelivers (idCart, x, y, z, w, districtTo, warTo, token) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the INSERT query
            preparedStatement.setInt(1, info.getIdCart());
            preparedStatement.setInt(2, info.getX());
            preparedStatement.setInt(3, info.getY());
            preparedStatement.setInt(4, info.getZ());
            preparedStatement.setInt(5, info.getW());
            preparedStatement.setString(6, info.getDistrictTo());
            preparedStatement.setString(7, info.getWarTo());
            preparedStatement.setString(8, info.getToken());

            // Execute the query
            int affectedRows = preparedStatement.executeUpdate();

            // Return true if the insertion was successful
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return false if an error occurred
        return false;
    }
}
