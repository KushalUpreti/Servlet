package utils;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JSONUtils {

    public static String jsonParser(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try {
            String json;
            while ((json = request.getReader().readLine()) != null) {
                sb.append(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPasswordMatch(String password, String hash) {
        return BCrypt.checkpw(password,hash);
    }
}
