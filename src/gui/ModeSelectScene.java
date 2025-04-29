package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;

public class ModeSelectScene {

    public static Scene getModeSelectScene(Stage primaryStage) {
        // 背景图
        ImageView background = new ImageView(new Image(
                ModeSelectScene.class.getResource("/assets/mode_bg.png").toExternalForm()
        ));
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 标题文字
        Label title = new Label("CHOOSE YOUR MODE");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#33475b"));

        // 单人模式按钮
        Button singlePlayerBtn = new Button("Single Player");
        singlePlayerBtn.setPrefSize(300, 50);
        singlePlayerBtn.setFont(Font.font(16));
        singlePlayerBtn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #2e7d32; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #a5d6a7; " +
                        "-fx-border-radius: 15;"
        );
        singlePlayerBtn.setOnAction(e -> primaryStage.setScene(GameBoardGUI.getGameScene(false)));

        // 双人模式按钮
        Button twoPlayerBtn = new Button("Two Players");
        twoPlayerBtn.setPrefSize(300, 50);
        twoPlayerBtn.setFont(Font.font(16));
        twoPlayerBtn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #1565c0; " +
                        "-fx-background-radius: 15; " +
                        "-fx-border-color: #90caf9; " +
                        "-fx-border-radius: 15;"
        );
        twoPlayerBtn.setOnAction(e -> primaryStage.setScene(GameBoardGUI.getGameScene(true)));

        // 返回按钮
        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font(12));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #33475b;");
        backBtn.setOnAction(e -> primaryStage.setScene(LoginScene.getLoginScene(primaryStage)));

        // 按钮容器
        VBox vbox = new VBox(20, title, singlePlayerBtn, twoPlayerBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(background, vbox);
        return new Scene(root, 800, 600);
    }
}
