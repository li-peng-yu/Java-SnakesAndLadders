package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
        signInButton.setPrefWidth(200);
        signInButton.setPrefHeight(40);
        signInButton.setOnAction(e -> {
            primaryStage.setScene(SignInScene.getSignInScene(primaryStage));
        });

        // 注册按钮
        Button registerButton = new Button("Register");
        registerButton.setPrefWidth(200);
        registerButton.setPrefHeight(40);
        registerButton.setOnAction(e -> {
            primaryStage.setScene(RegisterScene.getRegisterScene(primaryStage));
        });

        // 按钮布局
        VBox vbox = new VBox(20, signInButton, registerButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(background, vbox);

        return new Scene(root, 800, 600);
    }
}