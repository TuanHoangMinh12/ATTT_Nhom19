

package service;

import model.Cart;

import java.sql.*;

public class CartService {

    public int addCart(Cart cart) {
        String query = "INSERT INTO carts (idUser, timeShip, feeShip, totalPrice, infoShip, create_time, verify) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the INSERT query
            preparedStatement.setInt(1, cart.getIdUser());
            preparedStatement.setString(2, cart.getTimeShip());
            preparedStatement.setInt(3, cart.getFeeShip());
            preparedStatement.setInt(4, cart.getTotalPrice());
            preparedStatement.setInt(5, cart.getInfoShip());
            preparedStatement.setTimestamp(6, cart.getCreateTime());
            preparedStatement.setString(7, cart.getVerify());

            // Execute the query
            int affectedRows = preparedStatement.executeUpdate();

            // Check if the insert was successful and return the generated ID
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the ID of the new cart
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if the cart was not added successfully
    }
    public static void main(String[] args) {
        CartService cartService = new CartService();

        // Create a new Cart object
        Cart cart = new Cart();
        cart.setIdUser(1);
        cart.setTimeShip("2024-12-23 10:00:00");
        cart.setFeeShip(0);
        cart.setTotalPrice(50000);
        cart.setInfoShip(1);
        cart.setCreateTime(new Timestamp(System.currentTimeMillis()));
        cart.setVerify("Pending");

        // Add the cart and get the generated ID
        int cartId = cartService.addCart(cart);

        if (cartId != -1) {
            System.out.println("Cart added successfully with ID: " + cartId);
        } else {
            System.out.println("Failed to add cart.");
        }
    }
}
