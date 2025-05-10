package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameBoardGUI {

    private static final double BOARD_SIZE = 600;
    private static final double TOKEN_SIZE = 40;
    private static final double DICE_SIZE = 100;

    /**
     * @param primaryStage    主舞台
     * @param twoPlayersMode  true = 双人模式, false = 单人模式
     */
    public static Scene getGameScene(Stage primaryStage, boolean twoPlayersMode) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f2d5a0;");

        // —— Top: 回合提示 —— //
        Label turnLabel = new Label(twoPlayersMode ? "Player One's Turn" : "Player Two's Turn");
        Font pixelFont = Font.loadFont(
                GameBoardGUI.class.getResource("/assets/pixel_font.ttf").toExternalForm(),
                24
        );
        turnLabel.setFont(pixelFont);
        turnLabel.setTextFill(Color.WHITE);
        turnLabel.setStyle(
                "-fx-background-color: #2e7d32;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 10 20;"
        );
        BorderPane.setAlignment(turnLabel, Pos.CENTER);
        root.setTop(turnLabel);

        // —— Center: 棋盘 + 控制区 —— //
        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));

        // —— (1) 棋盘区 —— //
        Pane boardPane = new Pane();
        boardPane.setPrefSize(BOARD_SIZE, BOARD_SIZE);

        // 棋盘背景
        ImageView boardBg = new ImageView(
                new Image(GameBoardGUI.class.getResource("/assets/board.png").toExternalForm())
        );
        boardBg.setFitWidth(BOARD_SIZE);
        boardBg.setFitHeight(BOARD_SIZE);
        boardPane.getChildren().add(boardBg);
        boardPane.getChildren().addAll(
                // —— 梯子 —— //
                // 底部银色斜梯，跨越格子 3→20
                makeImage("/assets/ladder2.png", 60, 480, 120, 180),
                // 左中深色竖梯，大约跨 62→98
                makeImage("/assets/ladder1.png", 180, 220,  80, 260),
                // 中央棕色梯子，跨 54→78
                makeImage("/assets/ladder4.png", 260, 300, 100, 180),
                // 右上黄色梯子，跨 71→93
                makeImage("/assets/ladder3.png", 350, 140, 100, 200),

                // —— 蛇 —— //
                // 左侧红蛇，起于格 61→尾
                makeImage("/assets/snake1.png",   20, 100,  40, 240),
                // 中下黄色蛇，跨 25→47
                makeImage("/assets/snake2.png",  160, 380, 120, 140),
                // 顶部紫蛇，跨 74→96
                makeImage("/assets/snake3.png",  240,  40, 160, 200),
                // 右下绿色蛇，跨 29→ 5
                makeImage("/assets/snake4.png",  360, 360,  80, 200)
        );
        // 放置玩家棋子
        ImageView p1 = makeImage("/assets/pieceRed.png",    25, 530, TOKEN_SIZE, TOKEN_SIZE);
        boardPane.getChildren().add(p1);

        if (twoPlayersMode) {
            ImageView p2 = makeImage("/assets/pieceGreen.png", 55, 505, TOKEN_SIZE, TOKEN_SIZE);
            boardPane.getChildren().add(p2);
        }

        centerBox.getChildren().add(boardPane);

        // —— (2) 右侧控制区 —— //
        VBox controlBox = new VBox(30);
        controlBox.setAlignment(Pos.TOP_CENTER);

        // 骰子
        ImageView diceView = new ImageView(
                new Image(GameBoardGUI.class.getResource("/assets/dice1.png").toExternalForm())
        );
        diceView.setFitWidth(DICE_SIZE);
        diceView.setFitHeight(DICE_SIZE);
        diceView.setOnMouseClicked(e -> {
            // TODO: 掷骰子逻辑，更新 diceView.setImage(...)
        });

        controlBox.getChildren().add(diceView);

        // Options 面板容器
        StackPane optionsStack = new StackPane();
        optionsStack.setPrefWidth(250);  // 你可以根据需要设置宽高
        optionsStack.setPrefHeight(300);

// 背景图像
        ImageView optionsView = new ImageView(
                new Image(GameBoardGUI.class.getResource("/assets/option_background.png").toExternalForm())
        );
        optionsView.setPreserveRatio(false);
        optionsView.setFitWidth(250);   // 与 optionsStack 一致
        optionsView.setFitHeight(350);  // 与 optionsStack 一致

// 实际内容 VBox
        VBox optionsBox = new VBox(10);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(15));
        optionsBox.setPickOnBounds(false);  // 允许点击穿透空白区域
        optionsBox.setMouseTransparent(false); // 保证内容仍可被点击


// 音量图标
        optionsBox.getChildren().add(new Label("\uD83D\uDD0A") {{
            setFont(Font.font(24));
            setTextFill(Color.WHITE);
        }});

// 玩家 1 选色
        Label p1Label = new Label("Player One");
        p1Label.setFont(pixelFont);
        p1Label.setTextFill(Color.WHITE);
        optionsBox.getChildren().addAll(p1Label, makeColorRow());

        if (twoPlayersMode) {
            Label p2Label = new Label("Player Two");
            p2Label.setFont(pixelFont);
            p2Label.setTextFill(Color.WHITE);
            optionsBox.getChildren().addAll(p2Label, makeColorRow());
        }

        Rectangle restartClickArea = new Rectangle(130, 35); // 宽130，高35，正好覆盖“Restart Game”字样
        restartClickArea.setFill(Color.TRANSPARENT); // 设置为透明
        restartClickArea.setOnMouseClicked(e -> {
            primaryStage.setScene(ModeSelectScene.getModeSelectScene(primaryStage));
        });

// 设置对齐方式和边距 —— 微调位置
        StackPane.setAlignment(restartClickArea, Pos.BOTTOM_CENTER);
        StackPane.setMargin(restartClickArea, new Insets(0, 0, 25, 0)); // 距底12像素（向上推一点点）

// 添加到 optionsStack 中
        optionsStack.getChildren().addAll(optionsView, optionsBox,restartClickArea);
        controlBox.getChildren().add(optionsStack);
        centerBox.getChildren().add(controlBox);
        root.setCenter(centerBox);
        return new Scene(root, 1000, 700);
    }

    // 快捷创建 ImageView
    private static ImageView makeImage(String path,
                                       double x, double y,
                                       double w, double h) {
        ImageView iv = new ImageView(
                new Image(GameBoardGUI.class.getResource(path).toExternalForm())
        );
        iv.setFitWidth(w);
        iv.setFitHeight(h);
        iv.setLayoutX(x);
        iv.setLayoutY(y);
        return iv;
    }

    // 生成一行 4 个可点击的颜色小方块
    private static HBox makeColorRow() {
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER);
        for (Color c : new Color[]{ Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE }) {
            Rectangle r = new Rectangle(20, 20, c);
            r.setStroke(Color.WHITE); r.setStrokeWidth(1);
            row.getChildren().add(r);
        }
        return row;
    }
}