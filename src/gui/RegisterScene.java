package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;

import logic.UserManager;

import java.util.Random;

public class RegisterScene {

    private static Font baseFont = Font.loadFont(
            GameBoardGUI.class.getResourceAsStream("/assets/pixel_font.ttf"),
            12
    );

    private static String currentCode;  // 保存当前验证码

    public static Scene getRegisterScene(Stage primaryStage) {
        // 背景
        ImageView background = new ImageView(
                new Image(RegisterScene.class.getResource("/assets/login_bg.png").toExternalForm())
        );
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 标题
        Label title = new Label("Create Account");
        title.setFont(Font.font(
                baseFont.getFamily(),      // 字体族名
                FontWeight.BOLD,           // 加粗
                28                         // 字号
        ));

        title.setTextFill(Color.web("#33475b"));

        // 手机号 & 用户名
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.setFont(baseFont);
        phoneField.setMaxWidth(300);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setFont(baseFont);
        usernameField.setMaxWidth(300);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setFont(baseFont);
        passwordField.setMaxWidth(300);

        // 验证码输入 & 发送
        TextField codeField = new TextField();
        codeField.setPromptText("Verification Code ");
        codeField.setFont(baseFont);
        codeField.setMaxWidth(200);
        Button sendCodeBtn = new Button("Send Code");
        sendCodeBtn.setFont(baseFont);
        sendCodeBtn.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 15;"
        );
        sendCodeBtn.setOnAction(e -> {
            currentCode = String.format("%04d", new Random().nextInt(10000));
            // 这里模拟发送：实际应接入短信 API
            System.out.println("验证码已发送：" + currentCode);
            showAlert(Alert.AlertType.INFORMATION, "验证码为：" + currentCode);
        });

        // 注册按钮
        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(300);
        registerBtn.setFont(Font.font(14));
        registerBtn.setFont(Font.loadFont(
                LoginScene.class.getResource("/assets/pixel_font.ttf").toExternalForm(), 24));
        registerBtn.setStyle(
                "-fx-background-color: #4a90e2; f" +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 20;");
        registerBtn.setOnAction(e -> {
            String phone = phoneField.getText().trim();
            String user = usernameField.getText().trim();
            String pass = passwordField.getText();    // 读取密码！
            String code = codeField.getText().trim();

            if (phone.isEmpty() || user.isEmpty() || pass.isEmpty() || code.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Incomplete Data");
            } else if (!code.equals(currentCode)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Code");
            } else {
                if (UserManager.NewAccount(user)) {
                    UserManager.register(phone, user, pass);
                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful");
                    primaryStage.setScene(ModeSelectScene.getModeSelectScene(primaryStage));
                }
                else {
                    showAlert(Alert.AlertType.INFORMATION, "Username occupation");
                }
            }
        });

        // 返回按钮
        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font(12));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #33475b;");
        backBtn.setOnAction(e -> primaryStage.setScene(LoginScene.getLoginScene(primaryStage)));

        // 表单容器
        VBox formBox = new VBox(12,
                title,
                phoneField,
                usernameField,
                passwordField,
                new HBox(10, codeField, sendCodeBtn),
                registerBtn,
                backBtn
        );
        formBox.setAlignment(Pos.CENTER);
        formBox.setPadding(new Insets(20));
        formBox.setMaxWidth(360);
        formBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.8); " +
                        "-fx-background-radius: 15;"
        );

        StackPane root = new StackPane(background, formBox);
        return new Scene(root, 800, 600);
    }

    private static void showAlert(Alert.AlertType type, String header) {
        Alert alert = new Alert(type);
        alert.setTitle("");
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}