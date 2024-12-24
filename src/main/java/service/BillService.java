package service;


import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillService {

    public boolean addBill(Bill bill) {
        String query = "INSERT INTO bill (id_user, id_Book, idCart, shipping_info, id_discount, address, pack, payment_method, totalBill, quantity, phone, create_order_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for the INSERT query
            preparedStatement.setInt(1, bill.getIdUser());
            preparedStatement.setInt(2, bill.getIdBook());
            preparedStatement.setInt(3, bill.getIdCart());
            preparedStatement.setInt(4, bill.getShippingInfo());
            preparedStatement.setInt(5, bill.getIdDiscount());
            preparedStatement.setString(6, bill.getAddress());
            preparedStatement.setInt(7, bill.getPack());
            preparedStatement.setInt(8, bill.getPaymentMethod());
            preparedStatement.setDouble(9, bill.getTotalBill());
            preparedStatement.setInt(10, bill.getQuantity());
            preparedStatement.setString(11, bill.getPhone());
            preparedStatement.setDate(12, bill.getCreate_order_time());
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
