package vn.edu.hcmuaf.fit.controller.web.pdf;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class GeneratePdfServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển tiếp yêu cầu GET thành POST
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=order_details.pdf");

        // Lấy thông tin từ form
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String note = request.getParameter("note");

        // Lấy dữ liệu đơn hàng từ session
        Map<String, Object> cartOrder = (Map<String, Object>) request.getSession().getAttribute("cartOrder");

        try (PdfWriter writer = new PdfWriter(response.getOutputStream())) {
            PdfDocument pdfDocument = new PdfDocument(writer); // Tạo PdfDocument
            try (Document document = new Document(pdfDocument)) { // Tạo Document từ PdfDocument

                // Tiêu đề
                document.add(new Paragraph("Thông Tin Đơn Hàng").setBold().setFontSize(16));

                // Thông tin người dùng
                document.add(new Paragraph("Họ và Tên: " + name));
                document.add(new Paragraph("Điện Thoại: " + phone));
                document.add(new Paragraph("Email: " + email));
                document.add(new Paragraph("Địa Chỉ: " + address));
                document.add(new Paragraph("Ghi Chú: " + note));
                document.add(new Paragraph("\n"));

                // Chi tiết đơn hàng
                document.add(new Paragraph("Chi Tiết Đơn Hàng").setBold().setFontSize(16));
                Table table = new Table(new float[]{4, 2, 2}); // 3 cột
                table.addCell("Tên Sản Phẩm");
                table.addCell("Số Lượng");
                table.addCell("Mã Sản Phẩm");

                if (cartOrder != null) {
                    for (Map.Entry<String, Object> entry : cartOrder.entrySet()) {
                        Map<String, Object> item = (Map<String, Object>) entry.getValue();
                        table.addCell((String) item.get("productName"));
                        table.addCell(String.valueOf(item.get("quantity")));
                        table.addCell(entry.getKey());
                    }
                }

                document.add(table);

                // Tổng tiền
                double totalPrice = (double) request.getSession().getAttribute("totalPrice");
                document.add(new Paragraph("\nTổng Tiền: " + totalPrice + " đ"));
            }
        }
    }
}
