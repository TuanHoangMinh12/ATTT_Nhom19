package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String name;           // Họ và Tên
    private String phone;          // Điện Thoại
    private String email;          // Email
    private String address;        // Địa Chỉ
    private String city;           // Tỉnh/Thành
    private String district;       // Quận/Huyện
    private String ward;           // Phường/Xã
    private String total;          // Tổng Tiền
    private String finalTotal;     // Thành Tiền
    private String shippingFee;    // Phí Vận Chuyển
    private String packaging;      // Đóng Gói
    private String paymentMethod;  // Hình Thức Thanh Toán
    private String note;           // Ghi Chú
    private List<Product> products; // Danh sách sản phẩm

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public String getTotal() { return total; }
    public void setTotal(String total) { this.total = total; }

    public String getFinalTotal() { return finalTotal; }
    public void setFinalTotal(String finalTotal) { this.finalTotal = finalTotal; }

    public String getShippingFee() { return shippingFee; }
    public void setShippingFee(String shippingFee) { this.shippingFee = shippingFee; }

    public String getPackaging() { return packaging; }
    public void setPackaging(String packaging) { this.packaging = packaging; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }

    public void setProducts(List<Product> products) { this.products = products; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Họ và Tên: ").append(name).append("\n");
        sb.append("Điện Thoại: ").append(phone).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Địa Chỉ: ").append(address).append("\n");
        sb.append("Tỉnh/Thành: ").append(city).append("\n");
        sb.append("Quận/Huyện: ").append(district).append("\n");
        sb.append("Phường/Xã: ").append(ward).append("\n");
        sb.append("Tổng Tiền: ").append(total).append("\n");
        sb.append("Thành Tiền: ").append(finalTotal).append("\n");
        sb.append("Phí Vận Chuyển: ").append(shippingFee).append("\n");
        sb.append("Đóng Gói: ").append(packaging).append("\n");
        sb.append("Hình Thức Thanh Toán: ").append(paymentMethod).append("\n");
        sb.append("Ghi Chú: ").append(note).append("\n");
        sb.append("Chi Tiết Sản Phẩm:\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        return sb.toString();
    }
}
