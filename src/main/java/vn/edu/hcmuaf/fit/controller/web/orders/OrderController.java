package vn.edu.hcmuaf.fit.controller.web.orders;

import vn.edu.hcmuaf.fit.dao.ICustomerDAO;
import vn.edu.hcmuaf.fit.dao.IDiscountCustomerDAO;
import vn.edu.hcmuaf.fit.dao.IVoucherDAO;
import vn.edu.hcmuaf.fit.dao.impl.CartDao;
import vn.edu.hcmuaf.fit.dao.impl.CustomerDAO;
import vn.edu.hcmuaf.fit.dao.impl.DiscountCustomerDAO;
import vn.edu.hcmuaf.fit.dao.impl.VoucherDAO;
import vn.edu.hcmuaf.fit.model.CartModel;
import vn.edu.hcmuaf.fit.model.CustomerModel;
import vn.edu.hcmuaf.fit.services.IOrderService;
import vn.edu.hcmuaf.fit.services.impl.OrderService;
import vn.edu.hcmuaf.fit.utils.MessageParameterUntil;
import vn.edu.hcmuaf.fit.utils.SessionUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "order", value = "/order")
public class OrderController extends HttpServlet {
    private final IOrderService orderService = new OrderService();
    private final ICustomerDAO customerDAO = new CustomerDAO();
    private final IDiscountCustomerDAO discountCustomerDAO = new DiscountCustomerDAO();
    private final IVoucherDAO voucherDAO = new VoucherDAO();
CartDao cartDao =new CartDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerModel customer = (CustomerModel) SessionUtil.getInstance().getValue(request, "USERMODEL");
        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/login?action=login");
            return;
        }
        CustomerModel customer1 = (CustomerModel) request.getSession().getAttribute("customer");
        if (customer1 != null) {
            int orderCount = cartDao.countCartsByUserId(customer1.getIdUser()); // Ví dụ: lấy từ database
            HttpSession session = request.getSession();
            session.setAttribute("orderCount", orderCount);
        } else {
            System.out.println("Customer not found in session.");
        }

        String idVoucherParam = request.getParameter("idVoucher");
        if (idVoucherParam == null || idVoucherParam.isEmpty()) {
            new MessageParameterUntil("Mã giảm giá không tồn tại", "warning", "/views/web/cart.jsp", request, response).send();
            return;
        }

        try {
            int idVoucher = Integer.parseInt(idVoucherParam);

            if (idVoucher == 1000000) {
                setOrderNotVoucher(customer, request, response);
            } else if (!discountCustomerDAO.checkIdVoucherInCus(idVoucher, customer.getIdUser())) {
                new MessageParameterUntil("Mã giảm giá không tồn tại", "warning", "/views/web/cart.jsp", request, response).send();
            } else {
                setOrderVoucher(idVoucher, customer, request, response);
            }
        } catch (NumberFormatException e) {
            new MessageParameterUntil("Mã giảm giá không hợp lệ", "error", "/views/web/cart.jsp", request, response).send();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Bạn có thể xử lý các logic POST tại đây nếu cần.
    }

    private void setOrderNotVoucher(CustomerModel customer, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("customer", customerDAO.findById(customer.getIdUser()));
        request.getRequestDispatcher("/views/web/order.jsp").forward(request, response);
    }

    private void setOrderVoucher(int idVoucher, CustomerModel customer, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int priceDiscount = voucherDAO.getPriceVoucher(idVoucher);

        CartModel cartOrder = (CartModel) request.getSession().getAttribute("cartOrder");
        if (cartOrder == null) {
            cartOrder = new CartModel();
        }

        cartOrder.setVoucher(priceDiscount);
        cartOrder.setShip(30000);

        request.getSession().setAttribute("cartOrder", cartOrder);
        request.setAttribute("listDiscount", discountCustomerDAO.findDisByIdVou(customer.getIdUser(), idVoucher));
        request.setAttribute("customer", customerDAO.findById(customer.getIdUser()));
        request.getRequestDispatcher("/views/web/order.jsp").forward(request, response);
    }
}
