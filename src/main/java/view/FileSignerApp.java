package view;

import model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import util.RSAUtil;
import util.SHA256Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class FileSignerApp extends JFrame {
    private JTextField txtPrivateKey; // Ô nhập private key
    private JTextField txtFilePath;  // Ô hiển thị đường dẫn file
    private JButton btnChooseFile;  // Nút chọn file
    private JButton btnSign;        // Nút ký
    PdfReaderService reader = new PdfReaderService();
    RSAUtil rsa = new RSAUtil();
    SHA256Util sha256 = new SHA256Util();
    CartService cartService = new CartService();
    private String userEmail;  // Biến lưu email người dùng
    CustomerService customerService = new CustomerService();
    BillService billService = new BillService();
    InfomationDeliversService infomationDeliversService = new InfomationDeliversService();
    public  String email(){
        return userEmail;
    }
    public FileSignerApp(String email) {
        // Thiết lập cửa sổ
        setTitle("File Signer");
        setSize(900, 300); // Kích thước lớn hơn
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
         this.userEmail =email;
        System.out.println(userEmail);
        // Label và ô nhập private key
        JLabel lblPrivateKey = new JLabel("Private Key:");
        lblPrivateKey.setBounds(20, 20, 150, 40); // Tăng kích thước nhãn
        lblPrivateKey.setFont(new Font("Arial", Font.PLAIN, 16)); // Font lớn hơn
        add(lblPrivateKey);

        txtPrivateKey = new JTextField();
        txtPrivateKey.setBounds(180, 20, 580, 40); // Tăng chiều rộng và chiều cao
        txtPrivateKey.setFont(new Font("Arial", Font.PLAIN, 16)); // Font lớn hơn
        add(txtPrivateKey);

        // Label và ô nhập đường dẫn file
        JLabel lblFilePath = new JLabel("File Path:");
        lblFilePath.setBounds(20, 80, 150, 40); // Tăng kích thước nhãn
        lblFilePath.setFont(new Font("Arial", Font.PLAIN, 16)); // Font lớn hơn
        add(lblFilePath);

        txtFilePath = new JTextField();
        txtFilePath.setBounds(180, 80, 470, 40); // Tăng chiều rộng và chiều cao
        txtFilePath.setFont(new Font("Arial", Font.PLAIN, 16)); // Font lớn hơn
        txtFilePath.setEditable(false); // Không cho phép chỉnh sửa
        add(txtFilePath);

        btnChooseFile = new JButton("Choose File");
        btnChooseFile.setBounds(670, 80, 120, 40); // Tăng kích thước nút
        btnChooseFile.setFont(new Font("Arial", Font.PLAIN, 16)); // Font lớn hơn
        add(btnChooseFile);

        // Nút ký
        btnSign = new JButton("Sign");
        btnSign.setBounds(340, 150, 120, 50); // Tăng kích thước nút
        btnSign.setFont(new Font("Arial", Font.BOLD, 16)); // Font lớn hơn và in đậm
        add(btnSign);

        // Sự kiện chọn file
        btnChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    txtFilePath.setText(selectedFile.getAbsolutePath());
                    String path = selectedFile.getAbsolutePath();
                    System.out.println("Đường dẫn tệp: " + path);

                    // Validate the PDF file
                    if (!isValidPdf(selectedFile)) {
                        JOptionPane.showMessageDialog(null,
                                "Tệp PDF không hợp lệ hoặc bị lỗi. Vui lòng chọn một tệp khác.",
                                "Lỗi Tệp PDF",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        // Read content from the PDF file
                        String content = reader.readPdf(path);

                        // Parse the content into an Order object
                        Order order = reader.parseOrder(content);

                        // Display Order information
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

                        System.out.println("\nChi Tiết Sản Phẩm:");
                        for (Product product : order.getProducts()) {
                            System.out.println(product);
                        }

                    } catch (IOException ex) {
                        // Show error message for PDF reading issues
                        JOptionPane.showMessageDialog(null,
                                "Đã xảy ra lỗi khi đọc tệp PDF: " + ex.getMessage(),
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Sự kiện ký file
        btnSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String privateKey = txtPrivateKey.getText();
                String filePath = txtFilePath.getText();

                Date utilDate = new Date(); // Lấy ngày giờ hiện tại
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Chuyển đổi sang java.sql.Date
                String object ="";

                try {
                    // Read content from the PDF file
                    String content = reader.readPdf(filePath);

                    // Parse the content into an Order object
                    Order order   = reader.parseOrder(content);

                    // Display Order information
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

                    System.out.println("\nChi Tiết Sản Phẩm:");
                    for (Product product : order.getProducts()) {
                        object =order.getName()+ order.getPhone()+order.getEmail()+ order.getAddress()+ order.getDistrict()+order.getWard()+ order.getTotal()+order.getFinalTotal()+order.getShippingFee()+order.getPackaging()+order.getPaymentMethod()+order.getNote()+product.getProductName()+product.getQuantity()+product.getProductCode()+sqlDate;
                        System.out.println(product);
                    }
                    System.out.println(object);
                } catch (IOException ex) {
                    // Show error message for PDF reading issues
                    JOptionPane.showMessageDialog(null,
                            "Đã xảy ra lỗi khi đọc tệp PDF: " + ex.getMessage(),
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
                if (privateKey.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter your private key.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (filePath.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please choose a file.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String hash = sha256.check(object);
                System.out.println(hash);
                try {

                    String content = reader.readPdf(filePath);

                    // Parse the content into an Order object
                    Order order   = reader.parseOrder(content);

                    // Display Order information
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

                    System.out.println("\nChi Tiết Sản Phẩm:");
                    for (Product product : order.getProducts()) {
                        object =order.getName()+ order.getPhone()+order.getEmail()+ order.getAddress()+ order.getDistrict()+order.getWard()+ order.getTotal()+order.getFinalTotal()+order.getShippingFee()+order.getPackaging()+order.getPaymentMethod()+order.getNote()+product.getProductName()+product.getQuantity()+product.getProductCode();
                        System.out.println(product);
                        int id_book = Integer.parseInt(product.getProductCode());
                        int pack = Integer.parseInt(order.getPackaging());
                        int method = Integer.parseInt(order.getPaymentMethod());

                        rsa.setPrivateKey(privateKey);
                        String result = rsa.encrypt(hash);
                        System.out.println(result);

                        // Create a new Cart object
                        int id_user=    customerService.findUserIdByEmail(userEmail);
                        double value = Double.parseDouble(order.getTotal());
                        int total  = (int) value;
                        double final_total = Double.parseDouble(order.getFinalTotal());
                        Cart cart = new Cart();
                        cart.setIdUser(id_user);
                        cart.setTimeShip(null);
                        cart.setFeeShip(0);
                        cart.setTotalPrice(total);
                        cart.setInfoShip(1);
                        cart.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        cart.setVerify(result);

                        // Add the cart and get the generated ID
                        int cartId = cartService.addCart(cart);
                        Bill bill = new Bill(id_user, id_book,cartId,1, 0, order.getAddress() ,pack,method , final_total , product.getQuantity() , order.getPhone(), sqlDate);
                        billService.addBill(bill);
                        Infomationdelivers infomationdelivers = new Infomationdelivers(cartId,0,0,0,0,order.getDistrict(),order.getWard(),null);
                        infomationDeliversService.addInfomationDelivers(infomationdelivers);

                    }


                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                // Thực hiện ký file (giả lập)
                JOptionPane.showMessageDialog(null, "Đơn hàng đã được kí!", "Success", JOptionPane.INFORMATION_MESSAGE);
                txtPrivateKey.setText("");
                txtFilePath.setText("");

            }
        });
    }

    private boolean isValidPdf(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String email = (args.length > 0) ? args[0] : "default@example.com";
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FileSignerApp app = new FileSignerApp(email);
                app.setVisible(true);
            }
        });
    }
}
