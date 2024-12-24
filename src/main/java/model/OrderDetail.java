package model;

public class OrderDetail {
    private String productName;
    private int quantity;
    private String productId;

    public OrderDetail(String productName, int quantity, String productId) {
        this.productName = productName;
        this.quantity = quantity;
        this.productId = productId;
    }
 public  OrderDetail(){

 }
    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", productId='" + productId + '\'' +
                '}';
    }
}