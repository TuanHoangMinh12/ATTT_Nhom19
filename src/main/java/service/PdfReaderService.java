package service;

import model.Order;
import model.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PdfReaderService {

    /**
     * Đọc nội dung văn bản từ tệp PDF.
     *
     * @param filePath Đường dẫn đến tệp PDF.
     * @return Nội dung văn bản của tệp PDF.
     * @throws IOException Nếu có lỗi khi đọc tệp.
     */
    public String readPdf(String filePath) throws IOException {
        File file = new File(filePath);
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    public Order parseOrder(String content) {
        String[] lines = content.split("\n");
        Order order = new Order();
        List<Product> products = new ArrayList<>();

        boolean productSection = false;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("Họ và Tên:")) {
                order.setName(line.replace("Họ và Tên:", "").trim());
            } else if (line.startsWith("Điện Thoại:")) {
                order.setPhone(line.replace("Điện Thoại:", "").trim());
            } else if (line.startsWith("Email:")) {
                order.setEmail(line.replace("Email:", "").trim());
            } else if (line.startsWith("Địa Chỉ:")) {
                order.setAddress(line.replace("Địa Chỉ:", "").trim());
            } else if (line.startsWith("Tỉnh/Thành:")) {
                order.setCity(line.replace("Tỉnh/Thành:", "").trim());
            } else if (line.startsWith("Quận/Huyện:")) {
                order.setDistrict(line.replace("Quận/Huyện:", "").trim());
            } else if (line.startsWith("Phường/Xã:")) {
                order.setWard(line.replace("Phường/Xã:", "").trim());
            } else if (line.startsWith("Tổng Tiền:")) {
                order.setTotal(line.replace("Tổng Tiền:", "").replace("đ", "").trim());
            } else if (line.startsWith("Thành Tiền:")) {
                order.setFinalTotal(line.replace("Thành Tiền:", "").replace("đ", "").trim());
            } else if (line.startsWith("Phí Vận Chuyển:")) {
                order.setShippingFee(line.replace("Phí Vận Chuyển:", "").replace("đ", "").trim());
            } else if (line.startsWith("Đóng Gói:")) {
                order.setPackaging(line.replace("Đóng Gói:", "").trim());
            } else if (line.startsWith("Hình Thức Thanh Toán:")) {
                order.setPaymentMethod(line.replace("Hình Thức Thanh Toán:", "").trim());
            } else if (line.startsWith("Ghi Chú:")) {
                order.setNote(line.replace("Ghi Chú:", "").trim());
            } else if (line.startsWith("Tên Sản Phẩm")) {
                productSection = true;
                continue; // Skip header
            } else if (productSection) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    String productName = String.join(" ", Arrays.copyOfRange(parts, 0, parts.length - 2));
                    int quantity = Integer.parseInt(parts[parts.length - 2]);
                    String productCode = parts[parts.length - 1];

                    if (isNumeric(String.valueOf(quantity)) && isNumeric(productCode)) {
                        products.add(new Product(productName, quantity, productCode));
                    }
                }
            }
        }

        order.setProducts(products);
        return order;
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        PdfReaderService reader = new PdfReaderService();
        try {
            String filePath = "E:\\order_details_5.pdf"; // Đường dẫn tệp PDF
            String content = reader.readPdf(filePath); // Đọc nội dung từ tệp PDF

            Order order = reader.parseOrder(content); // Phân tích nội dung thành đối tượng Order

            // Hiển thị thông tin đơn hàng
            System.out.println("Thông tin đơn hàng:");
            System.out.println("Họ và Tên: " + order.getName());
            System.out.println("Điện Thoại: " + order.getPhone());
            System.out.println("Email: " + order.getEmail());
            System.out.println("Địa Chỉ: " + order.getAddress());
            System.out.println("Tỉnh/Thành: " + order.getCity());
            System.out.println("Quận/Huyện: " + order.getDistrict());
            System.out.println("Phường/Xã: " + order.getWard());
            System.out.println("Tổng Tiền: " + order.getTotal());
            System.out.println("Thành Tiền: " + order.getFinalTotal());
            System.out.println("Phí Vận Chuyển: " + order.getShippingFee());
            System.out.println("Đóng Gói: " + order.getPackaging());
            System.out.println("Hình Thức Thanh Toán: " + order.getPaymentMethod());
            System.out.println("Ghi Chú: " + order.getNote());

            // Hiển thị chi tiết sản phẩm
            System.out.println("\nChi Tiết Sản Phẩm:");
            for (Product product : order.getProducts()) {
                System.out.println(product);
            }

        } catch (IOException e) {
            System.err.println("Đã xảy ra lỗi khi đọc tệp PDF: " + e.getMessage());
        }
    }


}
