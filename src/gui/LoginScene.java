package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class LoginScene {

    public static Scene getLoginScene(Stage primaryStage) {
        // 背景图
        ImageView background = new ImageView(new Image(
                LoginScene.class.getResource("/assets/login_bg.png").toExternalForm()
        ));
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 自定义像素风格按钮
        Button signInBtn = createPixelButton("Sign In", () ->
                primaryStage.setScene(SignInScene.getSignInScene(primaryStage)));

        Button registerBtn = createPixelButton("Register", () ->
                primaryStage.setScene(RegisterScene.getRegisterScene(primaryStage)));

        Button guestBtn = createPixelButton("Guest Mode", () ->
                primaryStage.setScene(ModeSelectScene.getModeSelectScene(primaryStage)));

        VBox vbox = new VBox(20, signInBtn, registerBtn, guestBtn);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(background, vbox);
        return new Scene(root, 800, 600);
    }

    private static Button createPixelButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setFont(Font.loadFont(
                LoginScene.class.getResource("/assets/pixel_font.ttf").toExternalForm(), 24));
        button.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #fff; " +
                "-fx-background-radius: 0; " +
                "-fx-border-color: #000; " +
                "-fx-border-width: 2; " +
                "-fx-padding: 10 20; ");

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        button.setEffect(shadow);

        button.setOnAction(e -> action.run());
        return button;
    }
}
