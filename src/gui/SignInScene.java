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

public class SignInScene {

    public static Scene getSignInScene(Stage primaryStage) {
        // 背景
        ImageView background = new ImageView(
                new Image(SignInScene.class.getResource("/assets/login_bg.jpg").toExternalForm())
        );
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 标题
        Label title = new Label("Welcome Back");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#33475b"));

        // 用户名 & 密码
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        // 登录按钮
        Button signInBtn = new Button("Sign In");
        signInBtn.setPrefWidth(300);
        signInBtn.setFont(Font.font(14));
        signInBtn.setStyle(
                "-fx-background-color: #4a90e2; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 20;"
        );
        signInBtn.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText();
            if (user.isEmpty() || pass.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Incomplete Data", "请输入用户名和密码");
            } else if (UserManager.authenticate(user, pass)) {
                primaryStage.setScene(GameBoardGUI.getGameScene());
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "用户名或密码错误");
            }
        });

        // 游客模式
        Button guestBtn = new Button("Guest Mode");
        guestBtn.setPrefWidth(300);
        guestBtn.setFont(Font.font(14));
        guestBtn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #4a90e2; " +
                        "-fx-text-fill: #4a90e2; " +
                        "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20;"
        );
        guestBtn.setOnAction(e -> primaryStage.setScene(GameBoardGUI.getGameScene()));

        // 返回按钮
        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font(12));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #33475b;");
        backBtn.setOnAction(e -> primaryStage.setScene(LoginScene.getLoginScene(primaryStage)));

        // 表单容器
        VBox formBox = new VBox(15, title, usernameField, passwordField, signInBtn, guestBtn, backBtn);
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