package logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap; // 考虑并发安全

public class UserManager {

    // 模拟数据库（键为用户名，值为User对象）
    private static Map<String, User> users = new ConcurrentHashMap<>();
    private static final String USERS_FILE_PATH = "users.json"; // 定义JSON文件的路径
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create(); // 用于格式化JSON输出
    private static String currentUsername = null;
    static {
        loadUsersFromFile();
    }
    private static void loadUsersFromFile() {
        try {
            File file = new File(USERS_FILE_PATH);
            if (file.exists() && !file.isDirectory() && file.length() > 0) { // 检查文件是否存在且非空
                Reader reader = Files.newBufferedReader(Paths.get(USERS_FILE_PATH));
                Type type = new TypeToken<ConcurrentHashMap<String, User>>() {}.getType();
                users = gson.fromJson(reader, type);
                if (users == null) { // 如果文件内容为空或格式不正确，fromJson可能返回null
                    users = new ConcurrentHashMap<>();
                }
                reader.close();
                System.out.println("User data has been obtained from " + USERS_FILE_PATH + "load.");
            } else {
                users = new ConcurrentHashMap<>(); //确保 users 被初始化
            }
        } catch (IOException e) {
            System.err.println("Failed to load user data from the file:" + e.getMessage());
            users = new ConcurrentHashMap<>(); // 确保 users 被初始化
        }

        if (users != null) {
            for (Map.Entry<String, User> entry : users.entrySet()) {
                String username = entry.getKey();
                User user = entry.getValue();
                if (!isValidUser(username, user)) {
                    System.err.println("Warning: User data '" + username + "'invalid, Skipped.");
                    continue; // 跳过这个无效用户
                }
                users.put(username, user);
            }
        }
    }

    private static boolean isValidUser(String usernameInMap, User user) {
        if (user == null) return false;
        if (user.getUsername() == null || user.getUsername().trim().isEmpty() || !user.getUsername().equals(usernameInMap)) {
            // 检查用户名是否为空，或者 Map 中的 key 和 User 对象内部的 username 是否一致
            return false;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) { // 密码不能为空
            return false;
        }
        // 简单示例：电话号码只包含数字
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().matches("\\d+")) return false;
        return true;
    }


    // 检查用户名是否已存在
    public static boolean NewAccount(String username) {
        return !users.containsKey(username);
    }

    // 将用户数据保存到JSON文件
    private static synchronized void saveUsersToFile() {
        try (Writer writer = Files.newBufferedWriter(Paths.get(USERS_FILE_PATH))) {
            gson.toJson(users, writer);
            System.out.println("User data has been saved to " + USERS_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to save user data to a file: " + e.getMessage());
        }
    }

    // 注册新用户
    public static void register(String phone, String username, String password) {
        User newUser = new User(phone, username, password);
        users.put(username, newUser);
        saveUsersToFile(); // 注册新用户后保存数据
        System.out.println("user " + username + " registered");
        currentUsername = username;
    }

    // 登录验证
    public static boolean login(String username, String password) {
        User user = users.get(username);
        if(user !=null && user.getPassword().equals(password)) {
            currentUsername = username;
            return true;
        }
        currentUsername = null;
        return false;
    }
    public static String currentUsername() {
        return currentUsername;
    }

    // 用户类（内部静态类）
    private static class User {
        private String phone;
        private String username;
        private String password;

        public User(String phone, String username, String password) {
            this.phone = phone;
            this.username = username;
            this.password = password;
        }

        public String getPhoneNumber() {
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