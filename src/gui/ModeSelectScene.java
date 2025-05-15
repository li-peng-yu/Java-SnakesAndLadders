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
    private static Font baseFont = Font.loadFont(
            GameBoardGUI.class.getResourceAsStream("/assets/pixel_font.ttf"),
            28
    );
    public static Scene getModeSelectScene(Stage primaryStage) {
        // 背景图
        // >>> 根据彩蛋标记选择背景
        String bgPath = useEasterBackground
                ? "/assets/mode_select_easter.png"
                : "/assets/mode_bg.png";
        ImageView background = new ImageView(new Image(
                ModeSelectScene.class.getResource(bgPath).toExternalForm()));
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setPreserveRatio(false);

        // 标题文字（稍微上移）
        Label title = new Label("CHOOSE YOUR MODE");
        title.setFont(Font.font(
                baseFont.getFamily(),      // 字体族名
                FontWeight.BOLD,           // 加粗
                28                         // 字号
        ));
        title.setTextFill(Color.web("#2f3e46"));
        title.setTranslateY(-140);
        // 按钮统一尺寸与圆角美化
        Button singleBtn = createModeButton("Single Player", "#2e7d32", () ->{
            primaryStage.setScene(GameBoardGUI.getGameScene(primaryStage,false));}
        );

        Button twoBtn = createModeButton("Two Players", "#1565c0", () ->{
            primaryStage.setScene(GameBoardGUI.getGameScene(primaryStage,true));});

        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font("Arial", 12));
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #000;");
        backBtn.setOnAction(e -> {
            primaryStage.setScene(LoginScene.getLoginScene(primaryStage));
        });

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

    private static boolean useEasterBackground = false;
    public static void setUseEasterBackground(boolean flag) {
        useEasterBackground = flag;
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
