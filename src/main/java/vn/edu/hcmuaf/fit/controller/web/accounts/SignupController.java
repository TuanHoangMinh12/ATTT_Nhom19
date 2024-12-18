package vn.edu.hcmuaf.fit.controller.web.accounts;

import vn.edu.hcmuaf.fit.dao.impl.CustomerDAO;
import vn.edu.hcmuaf.fit.model.CustomerModel;
import vn.edu.hcmuaf.fit.utils.EmailUtil;
import vn.edu.hcmuaf.fit.utils.MessageParameterUntil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(name = "signup", value = "/signup")
public class SignupController extends HttpServlet {
    private static KeyPair keypair;
    private static PublicKey publicKey_khoa;
    private static PrivateKey privateKey_khoa;
    static String publicKeyStr;
    static String privateKeyStr;
    public static String keyToString(PrivateKey privateKey) {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        return Base64.getEncoder().encodeToString(keySpec.getEncoded());
    }

    public static String keyToString(PublicKey publicKey) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        return Base64.getEncoder().encodeToString(keySpec.getEncoded());
    }

    public static void create_key(){
        // create key
        KeyPairGenerator keyGenerator = null;
        try {
            keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(2048);
            keypair = keyGenerator.generateKeyPair();

            publicKey_khoa = keypair.getPublic();
            privateKey_khoa = keypair.getPrivate();
            // Convert the keys to string format
            privateKeyStr = keyToString(privateKey_khoa);
            publicKeyStr  = keyToString(publicKey_khoa);
            System.out.println(privateKeyStr );
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/web/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String re_pass = request.getParameter("password2");
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String phone = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Số luồng tối đa
        create_key();
        //String idUser = request.getParameter("id_user");
        if (!email.equals("") && !pass.equals("") && !re_pass.equals("") && !fname.equals("") && !lname.equals("")) {
            if (!pass.equals(re_pass)) {
                response.sendRedirect(request.getContextPath()+"/login");
            } else {
                CustomerDAO customerDAO = new CustomerDAO();
                CustomerModel account = customerDAO.checkAccountExist(email);
                if (account == null) {
                    EmailUtil sm = new EmailUtil();
                    String code = sm.getRandom();
                    CustomerModel user = new CustomerModel(email, pass, fname, lname, phone, address, code, System.currentTimeMillis() / 1000 / 60);
                    executorService.submit(() -> {
                        // Gửi email ở đây
                        sm.sendEmail(user);
                    });
                    HttpSession session = request.getSession();
                    session.setAttribute("registerUser", user);
                    session.setAttribute("public_key", publicKeyStr);
                    session.setAttribute("private_key", privateKeyStr);
                    session.setAttribute("toEmail", user.getEmail());

                    new MessageParameterUntil("Chúng tôi đã gửi mã xác minh đến email của bạn", "success", "/views/web/confirmRegister.jsp", request, response).send();
                } else {
                    if (customerDAO.getTypeLogin(account.getEmail()) == 2) {
                        new MessageParameterUntil("Tài khoản này của bạn đã được đăng nhập bằng tài khoản google", "danger", "/views/web/confirmRegister.jsp", request, response).send();
                    } else {
                        new MessageParameterUntil("Email đã tồn tại", "danger", "/views/web/signup.jsp", request, response).send();
                    }

                }
            }
        }else {
            new MessageParameterUntil("Vui lòng nhập thông tin đầy đủ", "danger", "/views/web/signup.jsp", request, response).send();

            }

        }

    public static void main(String[] args) {
        create_key();
    }
    }

