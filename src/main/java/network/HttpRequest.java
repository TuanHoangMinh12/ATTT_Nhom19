package network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static String getUserIdFromCookie(String urlString) throws IOException {
        // Tạo đối tượng URL từ đường dẫn trang web
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Gửi yêu cầu GET
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Nhận cookies từ response header
        String cookiesHeader = connection.getHeaderField("Set-Cookie");

        // Tìm cookie có tên là "userId"
        String userId = null;
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader.split(";")) {
                if (cookie.contains("id")) {
                    userId = cookie.split("=")[1];
                    break;
                }
            }
        }
        connection.disconnect();
        return userId;
    }
}
