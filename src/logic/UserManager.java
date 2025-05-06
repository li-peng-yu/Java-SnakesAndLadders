package logic;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    // 模拟数据库（键为用户名，值为User对象）
    private static final Map<String, User> users = new HashMap<>();

    // 注册新账号
    public static void register(String phone, String username, String password) {
        users.put(username, new User(phone, username, password));
    }

    // 登录验证
    public static boolean login(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    // 检查用户名是否已被占用
    public static boolean NewAccount(String username) {
        return !users.containsKey(username);
    }

    // 用户类（内部静态类）
    private static class User {
        private final String phone;
        private final String username;
        private final String password;

        public User(String phone, String username, String password) {
            this.phone = phone;
            this.username = username;
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}