package nhom19.hcmuaf.controller.admin;

import nhom19.hcmuaf.beans.Contact;
import nhom19.hcmuaf.beans.Users;
import nhom19.hcmuaf.dao.ContactDAO;
import nhom19.hcmuaf.util.MyUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ContactFormUser", value = "/admin/contact-form")
public class ContactFormUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users admin = MyUtils.getLoginedUser(session);
        ContactDAO contactDAO = new ContactDAO();
        List<Contact> listContact = contactDAO.layHetLienHeCuaKhachHang();
        request.setAttribute("listContact",listContact);
        request.setAttribute("admin", admin);
       RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/contact-from-user.jsp");
       dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}