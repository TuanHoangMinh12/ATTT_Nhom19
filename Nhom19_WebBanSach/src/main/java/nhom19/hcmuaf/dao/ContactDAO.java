package nhom19.hcmuaf.dao;

import nhom19.hcmuaf.beans.Contact;
import nhom19.hcmuaf.database.JDBIConnector;

import java.util.List;

public class ContactDAO {
    public void themLienHe(String ten, String email, String chuDe, String tinNhan) {
        JDBIConnector.get().withHandle(h ->{
            return h.createUpdate("INSERT INTO contacts(fullName,email,topic,message) values (:fullName,:email,:topic,:message )")
                    .bind("fullName",ten)
                    .bind("email",email)
                    .bind("topic",chuDe)
                    .bind("message",tinNhan)
                    .execute();
        });
    }
    public List<Contact> layHetLienHeCuaKhachHang() {
      return   JDBIConnector.get().withHandle(h ->{
            return h.createQuery("SELECT * from contacts").mapToBean(Contact.class).list();

        });
    };

}
