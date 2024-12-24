package model;


public class Product {
    private String productName;
    private int quantity;
    private String productCode;

    // Constructor
    public Product(String productName, int quantity, String productCode) {
        this.productName = productName;
        this.quantity = quantity;
        this.productCode = productCode;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "Tên: " + productName + "\n" +
                "Số lượng: " + quantity + "\n" +
                "Mã sản phẩm: " + productCode;
    }
}
