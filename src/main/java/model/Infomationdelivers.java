package model;

public class Infomationdelivers {

    private int id;             // id
    private int idCart;         // idCart
    private int x;              // x
    private int y;              // y
    private int z;              // z
    private int w;              // w
    private String districtTo;  // districtTo
    private String warTo;       // warTo
    private String token;       // token

    // Constructors
    public Infomationdelivers() {}

    public Infomationdelivers( int idCart, int x, int y, int z, int w, String districtTo, String warTo, String token) {

        this.idCart = idCart;
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.districtTo = districtTo;
        this.warTo = warTo;
        this.token = token;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public String getDistrictTo() {
        return districtTo;
    }

    public void setDistrictTo(String districtTo) {
        this.districtTo = districtTo;
    }

    public String getWarTo() {
        return warTo;
    }

    public void setWarTo(String warTo) {
        this.warTo = warTo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Optional: Override toString for debugging
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", idCart=" + idCart +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                ", districtTo='" + districtTo + '\'' +
                ", warTo='" + warTo + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
