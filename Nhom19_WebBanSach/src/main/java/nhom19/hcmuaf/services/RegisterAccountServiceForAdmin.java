package nhom19.hcmuaf.services;

import nhom19.hcmuaf.dao.UsersDao;
import nhom19.hcmuaf.dao.UsersDaoImpl;

import java.sql.Date;

public class RegisterAccountServiceForAdmin  {
    private static RegisterAccountServiceForAdmin instance;
    private UsersDao usersDao;

    public RegisterAccountServiceForAdmin() {
        usersDao = new UsersDaoImpl();
    }

    public static RegisterAccountServiceForAdmin getInstance() {
        if (instance == null) {
            instance = new RegisterAccountServiceForAdmin();
        }
        return instance;
    }

    public String RegisterUser(String name, String hashed, String encodePass, String emailAccount, String diaChi, String sdt, String img, String dob, String gioiTinh, int quyenHan) {

        String result = "";
        String username =name;
        String phoneNumber =sdt;
        String address = diaChi;
        String email = emailAccount;
        String password =encodePass;
        String hash =hashed;
        String sexual= gioiTinh;
        String linkIMG = img;
        int role = quyenHan;
        String ngaySinh =dob;

        result = usersDao.addNewUserOfAdmin(username,password,hash,email,phoneNumber,address, Date.valueOf(ngaySinh),sexual,linkIMG,role);
        return result;
    }

}
