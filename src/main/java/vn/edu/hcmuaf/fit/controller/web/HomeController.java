package vn.edu.hcmuaf.fit.controller.web;

import vn.edu.hcmuaf.fit.bean.Log;
import vn.edu.hcmuaf.fit.dao.IBookDAO;
import vn.edu.hcmuaf.fit.dao.ISlidePr;
import vn.edu.hcmuaf.fit.dao.impl.BLockUserDAO;
import vn.edu.hcmuaf.fit.dao.impl.BookDAO;
import vn.edu.hcmuaf.fit.dao.impl.SlidePrDAO;
import vn.edu.hcmuaf.fit.db.MessageProperties;
import vn.edu.hcmuaf.fit.model.CustomerModel;
import vn.edu.hcmuaf.fit.services.IAuthorService;
import vn.edu.hcmuaf.fit.services.ICustomerService;
import vn.edu.hcmuaf.fit.services.impl.AuthorService;
import vn.edu.hcmuaf.fit.utils.MD5Utils;
import vn.edu.hcmuaf.fit.utils.MessageParameterUntil;
import vn.edu.hcmuaf.fit.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;

@WebServlet(urlPatterns = { "/home", "/login", "/logout" })
public class HomeController extends HttpServlet {
    @Inject
    private ICustomerService customerService;
    ISlidePr slidePr = new SlidePrDAO();
    IBookDAO iBookDAO = new BookDAO();

    private int index = 0;

    IAuthorService authorService = new AuthorService();

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");
//        String action = req.getParameter("action");
//
//        // Kiểm tra cookie USER_EMAIL
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                // In ra tất cả cookie để kiểm tra
//                System.out.println("Cookie Name: " + cookie.getName() + ", Value: " + cookie.getValue());
//
//                if (cookie.getName().equals("USER_EMAIL")) {
//                    String email = cookie.getValue();
//                    System.out.println("USER_EMAIL cookie found with value: " + email); // In ra giá trị USER_EMAIL
//                    CustomerModel customer = customerService.findByUsername(email);
//                    if (customer != null) {
//                        // Nếu cookie tồn tại, khôi phục thông tin người dùng từ session hoặc cookie
//                        SessionUtil.getInstance().putValue(req, "USERMODEL", customer);
//                        SessionUtil.getInstance().putValue(req, "MAIL", customer.getEmail());
//                    }
//                    break;
//                }
//            }
//        } else {
//            System.out.println("No cookies found in the request.");
//        }
//
//        if (action != null && action.equals("login")) {
//            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
//        } else if (action != null && action.equals("logout")) {
//            // Khi người dùng đăng xuất, xóa cookie
//            Cookie cookie = new Cookie("USER_EMAIL", "");
//            cookie.setMaxAge(0); // Hết hạn
//            cookie.setPath("/"); // Xóa cookie khỏi toàn bộ ứng dụng
//            resp.addCookie(cookie);
//            SessionUtil.getInstance().removeValue(req, "USERMODEL");
//            req.getSession().invalidate();
//            resp.sendRedirect(req.getContextPath() + "/home");
//        } else {
//            req.setAttribute("listSlide", slidePr.findAll());
//            req.setAttribute("listBookPayTop", iBookDAO.listBookPayTop());
//            req.setAttribute("listBookMoiPhatHanh", iBookDAO.listBookNewReissue());
//            req.setAttribute("listBookSachPhatHanh", iBookDAO.listBookReissue());
//            req.setAttribute("listAuthor", authorService.find10Author());
//            req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
//        }
//    }
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html; charset=UTF-8");
    String action = req.getParameter("action");

    // Lấy cookie USER_EMAIL
    Cookie[] cookies = req.getCookies();
    String email = getCookieValue(cookies, "USER_EMAIL");

    if (email != null) {
        System.out.println("USER_EMAIL cookie found with value: " + email);
        CustomerModel customer = customerService.findByUsername(email);
        if (customer != null) {
            SessionUtil.getInstance().putValue(req, "USERMODEL", customer);
            SessionUtil.getInstance().putValue(req, "MAIL", customer.getEmail());
        }
    } else {
        System.out.println("No USER_EMAIL cookie found.");
    }

    // Xử lý các hành động khác
    if ("login".equals(action)) {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    } else if ("logout".equals(action)) {
        // Xóa cookie khi đăng xuất
        Cookie cookie = new Cookie("USER_EMAIL", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
        SessionUtil.getInstance().removeValue(req, "USERMODEL");
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/home");
    } else {
        req.setAttribute("listSlide", slidePr.findAll());
        req.setAttribute("listBookPayTop", iBookDAO.listBookPayTop());
        req.setAttribute("listBookMoiPhatHanh", iBookDAO.listBookNewReissue());
        req.setAttribute("listBookSachPhatHanh", iBookDAO.listBookReissue());
        req.setAttribute("listAuthor", authorService.find10Author());
        req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
    }
}
    private String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
        InetAddress myIP = InetAddress.getLocalHost();
        String ip = myIP.getHostAddress();

        if (action != null && action.equals("login")) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (email != null && password != null && !password.equals("")) {
                CustomerModel customer = customerService.findByUsernameAndPasswordAndStatus(email, MD5Utils.encrypt(password), 1);
                if (customer != null) {
                    // Reset tài khoản nếu cần thiết
                    BLockUserDAO.resetAccount(email);

                    // Lưu thông tin người dùng vào session
                    SessionUtil.getInstance().putValue(req, "USERMODEL", customer);

                    // Lưu thông tin người dùng vào cookie (email hoặc một mã bảo mật)
                    Cookie cookie = new Cookie("USER_EMAIL", email);
                    cookie.setMaxAge(30 * 60); // 30 phút
                    cookie.setPath("/"); // Đảm bảo cookie này có sẵn trong toàn bộ ứng dụng
                    resp.addCookie(cookie);

                    if (customer.getRole().equalsIgnoreCase("user")) {
                        SessionUtil.getInstance().putValue(req, "PASSWORD_USER", customer.getPassword());
                        SessionUtil.getInstance().putValue(req, "ID_USER", customer.getIdUser());
                        SessionUtil.getInstance().putValue(req, "MAIL", customer.getEmail());
                        resp.sendRedirect(req.getContextPath() + "/home");

                    } else if (customer.getRole().equalsIgnoreCase("admin")) {
                        resp.sendRedirect(req.getContextPath() + "/admin-root-home");

                    } else if (customer.getRole().equalsIgnoreCase("mod")) {
                        resp.sendRedirect(req.getContextPath() + "/admin-home");
                    }
                } else {
                    CustomerModel account = customerService.findByUsername(email);

                    if ((account != null) && BLockUserDAO.Attempts(email).equals("Updated")) {
                        Log log = new Log(Log.INFO, ip, "Login", account.getIdUser(), "Login fall", 1);
                        log.insert();
                        new MessageParameterUntil(MessageProperties.getUsername_password_invalid(), "danger", "/views/login.jsp", req, resp).send();
                    } else {
                        if ((account != null) && BLockUserDAO.Attempts(email).equals("block")) {
                            Log log = new Log(Log.ALER, ip, "Login", account.getIdUser(), "Account has been locked", 1);
                            log.insert();
                            new MessageParameterUntil("Your account has been locked, please contact your administrator to unlock it", "danger", "/views/login.jsp", req, resp).send();
                        } else {
                            new MessageParameterUntil(MessageProperties.getUsername_password_invalid(), "danger", "/views/login.jsp", req, resp).send();
                        }
                    }
                }
            } else {
                new MessageParameterUntil(MessageProperties.getUsername_password_invalid(), "danger", "/views/login.jsp", req, resp).send();
            }
        }
    }
}
