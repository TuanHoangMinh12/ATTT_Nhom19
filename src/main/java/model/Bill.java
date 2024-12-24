package model;

import java.sql.Date;

public class Bill {
    private int idOrder;         // id_order
    private int idUser;          // id_user
    private int idBook;          // id_book
    private int idCart;          // idCart
    private int shippingInfo;    // shipping_info
    private int idDiscount;      // id_discount
    private String address;      // address
    private int pack;            // pack
    private int paymentMethod;   // payment_method
    private double totalBill;    // totalBill
    private int quantity;        // quantity
    private String phone;        // phone
    private Date create_order_time;
    // Constructors


    public Bill( int idUser, int idBook, int idCart, int shippingInfo, int idDiscount, String address, int pack, int paymentMethod, double totalBill, int quantity, String phone, Date create_order_time) {

        this.idUser = idUser;
        this.idBook = idBook;
        this.idCart = idCart;
        this.shippingInfo = shippingInfo;
        this.idDiscount = idDiscount;
        this.address = address;
        this.pack = pack;
        this.paymentMethod = paymentMethod;
        this.totalBill = totalBill;
        this.quantity = quantity;
        this.phone = phone;
        this.create_order_time = create_order_time;
    }

    public Date getCreate_order_time() {
        return create_order_time;
    }

    // Getters and Setters
    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(int shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public int getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(int idDiscount) {
        this.idDiscount = idDiscount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPack() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Optional: Override toString for debugging
    @Override
    public String toString() {
        return "Bill{" +
                "idOrder=" + idOrder +
                ", idUser=" + idUser +
                ", idBook=" + idBook +
                ", idCart=" + idCart +
                ", shippingInfo=" + shippingInfo +
                ", idDiscount=" + idDiscount +
                ", address='" + address + '\'' +
                ", pack=" + pack +
                ", paymentMethod=" + paymentMethod +
                ", totalBill=" + totalBill +
                ", quantity=" + quantity +
                ", phone='" + phone + '\'' +
                '}';
    }
}

