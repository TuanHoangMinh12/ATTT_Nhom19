package model;

import java.sql.Timestamp;

public class Cart {
    private int id;             // id
    private int idUser;         // idUser
    private String timeShip;    // timeShip
    private int feeShip;        // feeShip
    private int totalPrice;     // totalPrice
    private int infoShip;       // infoShip
    private Timestamp createTime; // create_time
    private String verify;      // verify

    // Constructors
    public Cart() {}

    public Cart(int id, int idUser, String timeShip, int feeShip, int totalPrice, int infoShip, Timestamp createTime, String verify) {
        this.id = id;
        this.idUser = idUser;
        this.timeShip = timeShip;
        this.feeShip = feeShip;
        this.totalPrice = totalPrice;
        this.infoShip = infoShip;
        this.createTime = createTime;
        this.verify = verify;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTimeShip() {
        return timeShip;
    }

    public void setTimeShip(String timeShip) {
        this.timeShip = timeShip;
    }

    public int getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(int feeShip) {
        this.feeShip = feeShip;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getInfoShip() {
        return infoShip;
    }

    public void setInfoShip(int infoShip) {
        this.infoShip = infoShip;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    // Optional: Override toString for debugging
    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", timeShip='" + timeShip + '\'' +
                ", feeShip=" + feeShip +
                ", totalPrice=" + totalPrice +
                ", infoShip=" + infoShip +
                ", createTime=" + createTime +
                ", verify='" + verify + '\'' +
                '}';
    }
}
