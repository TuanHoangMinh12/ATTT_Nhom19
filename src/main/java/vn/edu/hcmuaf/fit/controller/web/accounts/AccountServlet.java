package vn.edu.hcmuaf.fit.controller.web.accounts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/accountt")
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("runJar".equals(action)) {
            // Đường dẫn đầy đủ tới file JAR
            String jarPath = "D:\\workspace\\ATHTTT\\projectCKATBM\\ATTT_Nhom19\\src\\tool\\toolKey12.jar";

            try {
                // Sử dụng ProcessBuilder để chạy file JAR
                ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarPath);
                Process process = processBuilder.start();

                // Đợi tiến trình hoàn tất
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    response.getWriter().write("File JAR đã được chạy thành công.");
                } else {
                    response.getWriter().write("Có lỗi xảy ra khi chạy file JAR.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write("Có lỗi xảy ra: " + e.getMessage());
            }
        }
    }
}

