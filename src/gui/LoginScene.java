package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginScene {

    public static Scene getLoginScene(Stage primaryStage) {
        // 背景图片
        ImageView background = new ImageView(new Image(LoginScene.class.getResource("/assets/login_bg.jpg").toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 登录按钮
        Button signInButton = new Button("Sign In");
        signInButton.setPrefWidth(300);
        signInButton.setPrefHeight(40);
        signInButton.setFont(Font.font(14));  // 加统一字体大小
        signInButton.setOnAction(e -> primaryStage.setScene(SignInScene.getSignInScene(primaryStage)));

        // 注册按钮
        Button registerButton = new Button("Register");
        registerButton.setPrefWidth(300);
        registerButton.setPrefHeight(40);
        registerButton.setFont(Font.font(14));  // 加统一字体大小
        8registerButton.setOnAction(e -> primaryStage.setScene(RegisterScene.getRegisterScene(primaryStage)));
        // 游客模式按钮
        Button guestBtn = new Button("Guest Mode");
        guestBtn.setPrefWidth(300);
        guestBtn.setPrefHeight(40);
        guestBtn.setFont(Font.font(14));  // 字体大小统一
        guestBtn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: #4a90e2; " +
                        "-fx-text-fill: #4a90e2; " +
                        "-fx-background-radius: 20; " +
                        "-fx-border-radius: 20;"
        );
        guestBtn.setOnAction(e -> primaryStage.setScene(GameBoardGUI.getGameScene()));

        VBox vbox = new VBox(20, signInButton, registerButton, guestBtn);
        vbox.setAlignment(Pos.CENTER);

        // 背景+按钮组合
        StackPane root = new StackPane(background, vbox);

        return new Scene(root, 800, 600);
    }
