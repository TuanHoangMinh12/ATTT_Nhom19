package vn.edu.hcmuaf.fit.controller.web.pdf;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/generate-pdf")
public class GeneratePdfServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=order_details.pdf");

        // Lấy thông tin từ form
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String pack = request.getParameter("pack");
        String pay = request.getParameter("pay");
        String note = request.getParameter("note");

        // Lấy thông tin chi tiết đơn hàng
        String[] productNames = request.getParameterValues("productNames[]");
        String[] productQuantities = request.getParameterValues("productQuantities[]");
        String[] productIds = request.getParameterValues("productIds[]");
        String totalPrice = request.getParameter("totalPrice");
        String shippingFee = request.getParameter("shippingFee");
        String voucher = request.getParameter("voucher");

        try (PdfWriter writer = new PdfWriter(response.getOutputStream());
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Đường dẫn tới font
            String fontPath = getServletContext().getRealPath("/WEB-INF/TimesNewRoman.ttf");
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H);

            // Cấu hình font cho tài liệu
            document.setFont(font);

            // Thêm thông tin khách hàng
            document.add(new Paragraph("Thông Tin Đơn Hàng").setFontSize(14).setBold());
            document.add(new Paragraph("Họ và Tên: " + name));
            document.add(new Paragraph("Điện Thoại: " + phone));
            document.add(new Paragraph("Email: " + email));
            document.add(new Paragraph("Địa Chỉ: " + address));
            document.add(new Paragraph("Tỉnh/Thành: " + city));
            document.add(new Paragraph("Quận/Huyện: " + district));
            document.add(new Paragraph("Phường/Xã: " + ward));
            document.add(new Paragraph("Đóng Gói: " + pack));
            document.add(new Paragraph("Hình Thức Thanh Toán: " + pay));
            document.add(new Paragraph("Ghi Chú: " + note));

            // Thêm chi tiết đơn hàng
            document.add(new Paragraph("\nChi Tiết Đơn Hàng").setFontSize(14).setBold());

            Table table = new Table(new float[]{3, 2, 2});
            table.addHeaderCell("Tên Sản Phẩm");
            table.addHeaderCell("Số Lượng");
            table.addHeaderCell("Mã Sản Phẩm");

            if (productNames != null && productQuantities != null && productIds != null) {
                for (int i = 0; i < productNames.length; i++) {
                    table.addCell(productNames[i]);
                    table.addCell(productQuantities[i]);
                    table.addCell(productIds[i]);
                }
            }

            document.add(table);

            // Thêm tổng tiền và thông tin thanh toán
            document.add(new Paragraph("\nTổng Tiền: " + totalPrice + " đ"));
            document.add(new Paragraph("Phí Vận Chuyển: " + shippingFee + " đ"));
            document.add(new Paragraph("Giảm Giá: " + voucher + " đ"));
            document.add(new Paragraph("Thành Tiền: " + (Double.parseDouble(totalPrice) - Double.parseDouble(voucher)) + " đ"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Đã xảy ra lỗi khi tạo file PDF: " + e.getMessage());
        }
    }
}
