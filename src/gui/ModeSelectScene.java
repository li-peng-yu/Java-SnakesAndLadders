package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ModeSelectScene {

    public static Scene getModeSelectScene(Stage primaryStage) {
        // 背景图
        ImageView background = new ImageView(new Image(
                ModeSelectScene.class.getResource("/assets/mode_bg.png").toExternalForm()
        ));
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 标题文字（稍微上移）
        Label title = new Label("CHOOSE YOUR MODE");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        title.setTextFill(Color.web("#2f3e46")); // 深灰蓝，更和背景协调
        title.setTranslateY(-140); // 上移

        // 按钮统一尺寸与圆角美化
        Button singleBtn = createModeButton("Single Player", "#2e7d32", () ->
                primaryStage.setScene(GameBoardGUI.getGameScene(false)));

        Button twoBtn = createModeButton("Two Players", "#1565c0", () ->
                primaryStage.setScene(GameBoardGUI.getGameScene(true)));

        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font("Arial", 12));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #000;");
        backBtn.setOnAction(e -> primaryStage.setScene(LoginScene.getLoginScene(primaryStage)));

        // 按钮容器（上下间距小一点，避免覆盖卡片）
        VBox buttonBox = new VBox(15, singleBtn, twoBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setTranslateY(-10); // 稍微抬起居中

        VBox rootVBox = new VBox(10, title, buttonBox, backBtn);
        rootVBox.setAlignment(Pos.CENTER);
        rootVBox.setPadding(new Insets(20));

        StackPane root = new StackPane(background, rootVBox);
        return new Scene(root, 800, 600);
    }

    private static Button createModeButton(String text, String color, Runnable action) {
        Button button = new Button(text);
        button.setPrefSize(240, 44);
        button.setFont(Font.font("Verdana", 15));
        button.setFont(Font.loadFont(
                LoginScene.class.getResource("/assets/pixel_font.ttf").toExternalForm(), 24));
        button.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #fff; " +
                "-fx-background-radius: 0; " +
                "-fx-border-color: #000; " +
                "-fx-border-width: 2; " +
                "-fx-padding: 10 20; ");
        button.setOnAction(e -> action.run());
        return button;
    }
}
