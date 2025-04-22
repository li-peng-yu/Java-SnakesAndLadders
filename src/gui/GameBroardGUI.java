package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GameBoardGUI {

    public static Scene getGameScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");
        // 在这里添加你的棋盘、按钮等 UI 元素
        return new Scene(root, 800, 600);
    }
}
