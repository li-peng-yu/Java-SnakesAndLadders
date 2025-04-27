package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class GameBoardGUI {

    public static Scene getGameScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");

        // 将绘制好的网格棋盘加入主界面中间
        root.setCenter(GridPaneBoard.createBoardGrid());

        return new Scene(root, 800, 600);
    }

}
