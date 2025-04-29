package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameBoardGUI {

    private static PlayerToken player1;
    private static PlayerToken player2;
    private static Pane tokenLayer;
    private static boolean isTwoPlayers = false;

    public static Scene getGameScene(boolean twoPlayersMode) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");

        StackPane boardStack = new StackPane();
        boardStack.getChildren().add(GridPaneBoard.createBoardGrid());

        tokenLayer = new Pane();
        boardStack.getChildren().add(tokenLayer);

        root.setCenter(boardStack);

        isTwoPlayers = twoPlayersMode;

        // 初始化棋子
        player1 = new PlayerToken("/assets/player1_avatar.png");
        tokenLayer.getChildren().add(player1.getAvatarNode());

        if (isTwoPlayers) {
            player2 = new PlayerToken("/assets/player2_avatar.png");
            tokenLayer.getChildren().add(player2.getAvatarNode());
        }

        return new Scene(root, 800, 600);
    }

    /**
     * 移动玩家棋子
     * @param playerNumber 玩家编号，1 或 2
     * @param newPosition 新的位置（格子编号）
     */
    public static void movePlayerTo(int playerNumber, int newPosition) {
        if (playerNumber == 1 && player1 != null) {
            player1.moveToPosition(newPosition);
        } else if (playerNumber == 2 && isTwoPlayers && player2 != null) {
            player2.moveToPosition(newPosition);
        }
    }

    public static void showWinScene(Stage primaryStage) {
        StackPane winRoot = new StackPane();
        winRoot.setStyle("-fx-background-color: white;");

        Label winLabel = new Label("You Win!");
        winLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        winLabel.setTextFill(Color.web("#FF6B6B"));
        winLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8,0,0,2);");

        Button backButton = new Button("Back to Main Menu");
        backButton.setFont(Font.font(18));
        backButton.setOnAction(e -> primaryStage.setScene(ModeSelectScene.getModeSelectScene(primaryStage)));

        ConfettiPane confettiPane = new ConfettiPane();

        StackPane.setAlignment(winLabel, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(backButton, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setMargin(backButton, new javafx.geometry.Insets(20));

        winRoot.getChildren().addAll(winLabel, backButton, confettiPane);

        Scene winScene = new Scene(winRoot, 800, 600);
        primaryStage.setScene(winScene);
    }
}
