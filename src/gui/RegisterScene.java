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
import logic.UserManager;

import java.util.Random;

public class RegisterScene {

    private static String currentCode;  // 保存当前验证码

    public static Scene getRegisterScene(Stage primaryStage) {
        // 背景
        ImageView background = new ImageView(
                new Image(RegisterScene.class.getResource("/assets/login_bg.jpg").toExternalForm())
        );
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 标题
        Label title = new Label("Create Account");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#33475b"));

        // 手机号 & 用户名
        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.setMaxWidth(300);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        // 验证码输入 & 发送
        TextField codeField = new TextField();
        codeField.setPromptText("Verification Code");
        codeField.setMaxWidth(180);
        Button sendCodeBtn = new Button("Send Code");
        sendCodeBtn.setFont(Font.font(12));
        sendCodeBtn.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 15;"
        );
        sendCodeBtn.setOnAction(e -> {
            currentCode = String.format("%04d", new Random().nextInt(10000));
            // 这里模拟发送：实际应接入短信 API
            System.out.println("验证码已发送：" + currentCode);
            showAlert(Alert.AlertType.INFORMATION, "验证码发送", "验证码为：" + currentCode);
        });

        // 注册按钮
        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(300);
        registerBtn.setFont(Font.font(14));
        registerBtn.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 20;"
        );
        registerBtn.setOnAction(e -> {
            String phone = phoneField.getText().trim();
            String user = usernameField.getText().trim();
            String code = codeField.getText().trim();
            if (phone.isEmpty() || user.isEmpty() || code.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Incomplete Data", "请完整填写所有信息");
            } else if (!code.equals(currentCode)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Code", "验证码错误");
            } else {
                UserManager.register(phone, user);
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful",
                        "初始密码已设为 123456，请返回登录。");
                primaryStage.setScene(LoginScene.getLoginScene(primaryStage));
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

    private static void showAlert(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle("");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}