package vn.edu.hcmuaf.fit.controller.web.pdf;


import vn.edu.hcmuaf.fit.dao.impl.CartDao;
import vn.edu.hcmuaf.fit.model.CustomerModel;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/check-order-status")
public class CheckOrderStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy email người dùng từ session

          CartDao cartDao = new CartDao();


        try {
            // Lấy số lượng đơn hàng từ Service
            CustomerModel customer = (CustomerModel) request.getSession().getAttribute("customer");
            if (customer != null) {
                int currentOrderCount = cartDao.countCartsByUserId(customer.getIdUser());
                // Trả về kết quả dưới dạng JSON
                response.setContentType("application/json");
                response.getWriter().write("{\"orderCount\": " + currentOrderCount + "}");
            } else {
                System.out.println("Customer not found in session.");
            }




        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to fetch order count.\"}");
            e.printStackTrace();
        }
    }
}
