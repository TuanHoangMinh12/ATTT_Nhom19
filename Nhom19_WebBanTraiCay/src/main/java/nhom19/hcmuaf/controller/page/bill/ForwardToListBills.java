package nhom19.hcmuaf.controller.page.bill;

import nhom19.hcmuaf.beans.Bills;
import nhom19.hcmuaf.beans.Users;
import nhom19.hcmuaf.dao.BillDao;
import nhom19.hcmuaf.dao.BillDaoImpl;
import nhom19.hcmuaf.util.MyUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ForwardToListBills", value = "/page/bill/forward-to-list-bill")
public class ForwardToListBills extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users users = MyUtils.getLoginedUser(session);
        BillDao orderDao = new BillDaoImpl();
        // biến này sẽ lưu tất cả các hóa đơn người dùng đã mua
        List<Bills> listBills = new ArrayList<>();
        listBills = orderDao.getListBills(users.getId());
        request.setAttribute("listBills",listBills);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/page/bill/list-bill");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}