package network;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CookieFetcher {
    public void fetchCookiesFromWebApp() throws Exception {
        // URL của trang web mà bạn muốn lấy cookie từ
        URL url = new URL("http://localhost:8080/page/user/user-profile");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Thiết lập HTTP request method
        connection.setRequestMethod("GET");

        // Kết nối và gửi yêu cầu
        connection.connect();

        // Lấy các headers từ HTTP response
        Map<String, List<String>> headers = connection.getHeaderFields();
        List<String> cookies = headers.get("Set-Cookie");

        if (cookies != null) {
            // Kiểm tra xem có cookie "id" không
            for (String cookie : cookies) {
                if (cookie.startsWith("id=")) {
                    String userId = cookie.split("=")[1]; // Lấy giá trị userId từ cookie
                    System.out.println("User ID from Cookie: " + userId);
                    // Bạn có thể sử dụng userId này để lưu public key vào DB
                }
            }
        }

        // Đảm bảo đóng kết nối
        connection.disconnect();
    }

    public static void main(String[] args) {
        try {
            new CookieFetcher().fetchCookiesFromWebApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
