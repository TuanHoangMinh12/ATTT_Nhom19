package vn.edu.hcmuaf.fit.controller.admin.managementOrder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "verifySignature", value = "/verifySignature")
public class VerifySignatureController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain; charset=UTF-8");
        String result;

        try {
            // Lấy ID từ request
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                throw new IllegalArgumentException("ID đơn hàng không được để trống.");
            }
            int id = Integer.parseInt(idParam);

            // Kiểm tra chữ ký từ cơ sở dữ liệu
            boolean isValid = checkSignatureFromDatabase(id);

            if (isValid) {
                result = "Đơn hàng đã được ký xác thực.";
            } else {
                result = "Đơn hàng đã bị chỉnh sửa.";
            }
        } catch (NumberFormatException e) {
            result = "ID đơn hàng phải là số.";
        } catch (IllegalArgumentException e) {
            result = e.getMessage();
        } catch (Exception e) {
            result = "Lỗi trong quá trình xử lý: " + e.getMessage();
        }

        // Gửi kết quả về client
        response.getWriter().write(result);
    }

}
