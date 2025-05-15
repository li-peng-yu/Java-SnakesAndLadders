package gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;        // 新增
import javafx.scene.media.MediaPlayer;  // 新增
import javafx.util.Duration;         // 新增 (用于 MediaPlayer.INDEFINITE)
import java.net.URL;
import javafx.animation.PauseTransition;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import logic.Gameengine;
// import gui.TokenAnimator; // Assuming TokenAnimator is in the same package or imported correctly
// import gui.ConfettiPane;  // Assuming ConfettiPane is in the same package or imported correctly

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import gui.PlayerToken;

public class GameBoardGUI {

    private static Font baseFont = Font.loadFont(
            GameBoardGUI.class.getResourceAsStream("/assets/pixel_font.ttf"),
            28
    );

    private static final double BOARD_SIZE = 600;
    private static final double TOKEN_SIZE = 40;
    private static final double DICE_SIZE = 100;
    private static final int PLAYER_VISUAL_OFFSET_X = 13; // X轴方向的偏移
    private static final int PLAYER_VISUAL_OFFSET_Y = 13; // Y轴方向的偏移
    private static MediaPlayer backgroundMusicPlayer;
    private static boolean isMusicPlaying = true; // 跟踪音乐播放状态
    // 新增：定义声音开启和关闭的图标
    private static final String SOUND_ON_ICON = "\uD83D\uDD0A"; // 带声波的扬声器
    private static final String SOUND_OFF_ICON = "\uD83D\uDD07"; // 带斜线的扬声器
    static PauseTransition idleTimer;
    private static ImageView easterImageView;
    private static boolean hasTriggeredEasterEgg = false;


    public static Scene getGameScene(Stage primaryStage, boolean twoPlayersMode) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f2d5a0;");
        StackPane sceneRoot = new StackPane(root);

        final boolean[] isPlayerOneTurn = { true };
        final Image playerOneTurnImage = loadImage("/assets/player_one.png");
        final Image playerTwoTurnImage = loadImage("/assets/player_two.png");

        final ImageView topTurnView = new ImageView(playerOneTurnImage);
        topTurnView.setPreserveRatio(false);
        topTurnView.setFitWidth(250);
        topTurnView.setFitHeight(50);
        BorderPane.setAlignment(topTurnView, Pos.CENTER);
        root.setTop(topTurnView);

        String musicUrl = GameBoardGUI.class
                .getResource("/assets/background.mp3")
                .toExternalForm();
        Media bgMedia = new Media(musicUrl);
        MediaPlayer bgPlayer = new MediaPlayer(bgMedia);
        bgPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgPlayer.setVolume(0.5);
        bgPlayer.play();

        final boolean[] soundOn = { true };

        Font pixelFont = Font.loadFont(
                GameBoardGUI.class.getResource("/assets/pixel_font.ttf").toExternalForm(), 24);
        if (pixelFont == null) {
            pixelFont = Font.font("Arial", 24);
        }

        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));

        Pane boardPane = new Pane();
        boardPane.setPrefSize(BOARD_SIZE, BOARD_SIZE);

        ImageView boardBg = createImageView("/assets/board.png", 0, 0, BOARD_SIZE, BOARD_SIZE);
        boardPane.getChildren().add(boardBg);

        boardPane.getChildren().addAll(
                createImageView("/assets/ladder2.png", 350, 205, 120, 240),
                createImageView("/assets/ladder1.png", 85, 355,  80, 200,-35),
                createImageView("/assets/ladder4.png", 130, 110, 77, 220),
                createImageView("/assets/ladder3.png", 465, 65, 96, 200,8),
                createImageView("/assets/snake1.png", 420, 400, 152, 124,-200),
                createImageView("/assets/snake2.png", 170, 320, 120, 125),
                createImageView("/assets/snake3.png", 270, 40, 170, 200,-10),
                createImageView("/assets/snake4.png", 50, 100, 80, 130),
                createImageView("/assets/prop1.png",35,305,50,50)
        );

        // 修改点1: 将 p1Piece 声明为 final
        final PlayerToken p1Token = new PlayerToken("/assets/pieceRed.png", 25, 530);
        final ImageView p1Piece = p1Token.getAvatarNode(); // 替代原来的 ImageView
        boardPane.getChildren().add(p1Piece);

        final PlayerToken p2Token;
        final ImageView p2Piece;

        if (twoPlayersMode) {
            p2Token = new PlayerToken("/assets/pieceGreen.png", 55, 505);
            p2Piece = p2Token.getAvatarNode();
            boardPane.getChildren().add(p2Piece);
        } else {
            p2Token = null;
            p2Piece = null;
        }


        centerBox.getChildren().add(boardPane);

        VBox controlBox = new VBox(30);
        controlBox.setAlignment(Pos.TOP_CENTER);

        Label soundIcon = new Label("\uD83D\uDD0A");
        soundIcon.setFont(Font.font(44));
        soundIcon.setTextFill(Color.WHITE);

        // —— 点击切换静音/取消静音 ——
        soundIcon.setOnMouseClicked(e -> {
            if (soundOn[0]) {
                bgPlayer.pause();
                soundOn[0] = false;
                soundIcon.setText("\uD83D\uDD07");       // 🔇
                soundIcon.setStyle("-fx-opacity: 0.5;");
            } else {
                bgPlayer.play();
                soundOn[0] = true;
                soundIcon.setText("\uD83D\uDD0A");       // 🔊
                soundIcon.setStyle("-fx-opacity: 1;");
            }
        });

        ImageView diceView = createImageView("/assets/dice1.png", 0, 0, DICE_SIZE, DICE_SIZE);
        final Image[] diceImages = new Image[6];
        for (int i = 0; i < 6; i++) {
            diceImages[i] = loadImage("/assets/dice" + (i + 1) + ".png");
        }

        Gameengine engine = new Gameengine(twoPlayersMode ?
                List.of("Player 1", "Player 2") : List.of("Player 1"));

        diceView.setOnMouseClicked(e -> {
            // 判断是 P1 还是 P2
            boolean isP1 = isPlayerOneTurn[0];
            // 获取当前玩家对象（记录在掷骰子前的位置）
            Gameengine.Player player = engine.getPlayerByName(isP1 ? "Player 1" : "Player 2");
            int startPos = player.position;

            // 掷骰子并更新模型（包含蛇梯逻辑）
            int steps = engine.rollDice();
            if (steps == -1 || steps == -2) return;  // 游戏结束或冻结时直接返回

            if (idleTimer != null) {
                idleTimer.stop();
                idleTimer.playFromStart();  // 重置计时器
            }

            // 更新骰子图片
            diceView.setImage(diceImages[steps - 1]);

            // 计算“原始落点” rawLanding（按步数 + 反弹，不含蛇梯）
            int rawLanding = startPos + steps;
            if (rawLanding > 100) {
                rawLanding = 100 - (rawLanding - 100);
            }

            // 获取“最终落点” finalPos（包含蛇／梯子，已由 engine.movePlayer 处理）
            int finalPos = player.position;
            final int finland = rawLanding;
            // 选择对应的棋子节点
            ImageView token = isP1 ? p1Piece : p2Piece;
            if (token != null) {
                new Thread(() -> {
                    try {
                        // —— 第一段：逐格跳跃到 rawLanding
                        int currentPos = startPos;
                        if (startPos+steps <= 100) {
                            for (int i = 0; i < steps; i++) {
                            int nextPos = currentPos + 1;

                                int[] from = getCoordinates(currentPos, isP1 ? "Player 1" : "Player 2");
                                int[] to   = getCoordinates(nextPos,    isP1 ? "Player 1" : "Player 2");
                                Platform.runLater(() ->
                                        TokenAnimator.jumpTo(token, from[0], from[1], to[0], to[1])
                                );
                                Thread.sleep(300);  // 每格跳跃间隔
                                currentPos = nextPos;
                            }
                        }
                        if (startPos+steps > 100) {
                            for (int i = 0; i < steps; i++) {
                                if(startPos+i+1<=100){
                                    int nextPos = currentPos + 1;
                                    int[] from = getCoordinates(currentPos, isP1 ? "Player 1" : "Player 2");
                                    int[] to   = getCoordinates(nextPos,    isP1 ? "Player 1" : "Player 2");
                                    Platform.runLater(() ->
                                            TokenAnimator.jumpTo(token, from[0], from[1], to[0], to[1])
                                    );
                                    Thread.sleep(300);  // 每格跳跃间隔
                                    currentPos = nextPos;
                                }
                                else{
                                    int nextPos = currentPos - 1;
                                    int[] from = getCoordinates(currentPos, isP1 ? "Player 1" : "Player 2");
                                    int[] to   = getCoordinates(nextPos,    isP1 ? "Player 1" : "Player 2");
                                    Platform.runLater(() ->
                                            TokenAnimator.jumpTo(token, from[0], from[1], to[0], to[1])
                                    );
                                    Thread.sleep(300);  // 每格跳跃间隔
                                    currentPos = nextPos;
                                }
                            }
                        }


                        // —— 到达 rawLanding 后短暂停顿
                        Thread.sleep(300);

                        // —— 第二段：如踩到蛇／梯，滑动到 finalPos
                        if (finland != finalPos) {
                            int[] from = getCoordinates(finland, isP1 ? "Player 1" : "Player 2");
                            int[] to   = getCoordinates(finalPos,  isP1 ? "Player 1" : "Player 2");
                            Platform.runLater(() ->
                                    TokenAnimator.slideTo(token, from[0], from[1], to[0], to[1])
                            );
                            Thread.sleep(1000);  // 滑动时长
                        }

                        Thread.sleep(300);

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }

            // 游戏结束判定（原有 confetti 逻辑）
            if (engine.isGameOver()) {
                if (idleTimer != null) {
                    idleTimer.stop();
                    idleTimer = null; // 可选：清空引用
                }
                if (soundOn[0]) {
                    bgPlayer.pause();
                    soundOn[0] = false;
                    soundIcon.setText("\uD83D\uDD07");       // 🔇
                    soundIcon.setStyle("-fx-opacity: 0.5;");}
                ConfettiPane confettiPane = new ConfettiPane(primaryStage);
                sceneRoot.getChildren().add(confettiPane);

                Label winnerLabel = new Label("Player " + engine.getWinner().name + " Win!");
                winnerLabel.setFont(Font.font(
                        baseFont.getFamily(),      // 字体族名
                        FontWeight.BOLD,           // 加粗
                        40                         // 字号
                ));
                winnerLabel.setTextFill(Color.WHITE);
                StackPane.setAlignment(winnerLabel, Pos.CENTER);
                sceneRoot.getChildren().add(winnerLabel);
            }

            // 切换回合并更新提示
            if (twoPlayersMode) {
                isPlayerOneTurn[0] = !isPlayerOneTurn[0];
                topTurnView.setImage(
                        isPlayerOneTurn[0] ? playerOneTurnImage : playerTwoTurnImage
                );
            }
        });


        controlBox.getChildren().add(diceView);

        StackPane optionsStack = new StackPane();
        optionsStack.setPrefWidth(250);
        optionsStack.setPrefHeight(350);

        ImageView optionsBgView = createImageView("/assets/option_background.png", 0, 0, 250, 350);
        optionsBgView.setPreserveRatio(false);

        VBox optionsContentBox = new VBox(10);
        optionsContentBox.setAlignment(Pos.CENTER);
        optionsContentBox.setPadding(new Insets(15));
        optionsContentBox.setPickOnBounds(false);



        optionsContentBox.getChildren().add(soundIcon);


        Label p1Label = new Label("Player One");
        p1Label.setFont(pixelFont);
        p1Label.setTextFill(Color.WHITE);
        optionsContentBox.getChildren().addAll(p1Label, makeColorRow(p1Piece, Color.RED)); // p1Piece 是 final

        // 使用修改后的 p2Piece。如果 twoPlayersMode 为 true，则 p2Piece 不为 null。
        if (twoPlayersMode) { // p2Piece 在 twoPlayersMode=true 时已确保被正确初始化
            Label p2Label = new Label("Player Two");
            p2Label.setFont(pixelFont);
            p2Label.setTextFill(Color.WHITE);
            // p2Piece 在这里不为 null，因为上面的 if (twoPlayersMode) 条件
            optionsContentBox.getChildren().addAll(p2Label, makeColorRow(p2Piece, Color.GREEN));
        }

        Rectangle restartClickArea = new Rectangle(130, 35);
        restartClickArea.setFill(Color.TRANSPARENT);
        restartClickArea.setOnMouseClicked(e -> {
            if (soundOn[0]) {
                bgPlayer.pause();
                soundOn[0] = false;
                soundIcon.setText("\uD83D\uDD07");       // 🔇
                soundIcon.setStyle("-fx-opacity: 0.5;");}
            if (idleTimer != null) {
                idleTimer.stop();
                idleTimer = null; // 可选：清空引用
            }
            primaryStage.setScene(ModeSelectScene.getModeSelectScene(primaryStage));
        });
        StackPane.setAlignment(restartClickArea, Pos.BOTTOM_CENTER);
        StackPane.setMargin(restartClickArea, new Insets(0, 0, 25, 0));

        optionsStack.getChildren().addAll(optionsBgView, optionsContentBox, restartClickArea);
        controlBox.getChildren().add(optionsStack);

        centerBox.getChildren().add(controlBox);
        root.setCenter(centerBox);

        easterImageView = new ImageView(new Image(GameBoardGUI.class.getResource("/assets/easter.png").toExternalForm()));
        easterImageView.setOpacity(0);
        easterImageView.setLayoutX(350); // 自行调整位置
        easterImageView.setLayoutY(200);
        easterImageView.setFitWidth(320);
        easterImageView.setFitHeight(320);

// 初始化定时器
        idleTimer = new PauseTransition(Duration.seconds(10));
        idleTimer.setOnFinished(evt -> {

            if (hasTriggeredEasterEgg) return;

            hasTriggeredEasterEgg = true; // 设置标志位

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), easterImageView);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            FadeTransition stay = new FadeTransition(Duration.seconds(2), easterImageView);
            stay.setFromValue(1);
            stay.setToValue(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), easterImageView);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            SequentialTransition seq = new SequentialTransition(fadeIn, stay, fadeOut);
            seq.setOnFinished(ev -> root.getChildren().remove(easterImageView));

            if (!root.getChildren().contains(easterImageView)) {
                root.getChildren().add(easterImageView);
            }
            seq.play();

            ModeSelectScene.setUseEasterBackground(true);
        });

// 启动定时器
        idleTimer.play();


        return new Scene(sceneRoot, 1000, 700);
    }

    private static Image loadImage(String path) {
        try {
            // Ensure the path starts with "/" if it's relative to the classpath root
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            return new Image(GameBoardGUI.class.getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Failed to load image: " + path);
            // e.printStackTrace(); // For debugging
            return null;
        }
    }

    private static ImageView createImageView(String path, double x, double y, double w, double h, double rotateAngle) {
        ImageView iv = createImageView(path, x, y, w, h);
        iv.setRotate(rotateAngle);
        return iv;
    }

    private static ImageView createImageView(String path, double x, double y, double w, double h) {
        Image img = loadImage(path);
        ImageView iv = (img != null) ? new ImageView(img) : new ImageView(); // Creates an empty ImageView if image fails to load
        iv.setFitWidth(w);
        iv.setFitHeight(h);
        iv.setLayoutX(x);
        iv.setLayoutY(y);
        if ("/assets/ladder1.png".equals(path)) { // Be careful with path comparison if it could have leading slash variations
            iv.setScaleX(-1);
        }
        iv.setSmooth(false); // For pixel art, false is often better
        iv.setPreserveRatio(true);
        return iv;
    }

    private static int[] getCoordinates(int position, String playerName) {
        if (position < 1 || position > 100) { // 基本验证
            System.err.println("Invalid position: " + position + ". Defaulting to 1.");
            position = 1;
        }
        int row = (position - 1) / 10;
        int col = (position - 1) % 10;
        if (row % 2 != 0) { // 奇数行反向
            col = 9 - col;
        }

        // 假设每个格子的尺寸是 53
        int cellDimension = 53; // (int) (BOARD_SIZE / 10)
        // TOKEN_SIZE 应该是已定义的常量, e.g., 40

        // 计算单个棋子在格子中居中时的左上角偏移量
        int baseOffsetX = (cellDimension - (int)TOKEN_SIZE) / 2; // (60 - 40) / 2 = 10
        int baseOffsetY = (cellDimension - (int)TOKEN_SIZE) / 2;

        // 计算棋子在格子中居中时的基础X, Y坐标 (相对于boardPane的0,0)
        int calculatedX = col * cellDimension + baseOffsetX;
        int calculatedY = (9 - row) * cellDimension + baseOffsetY;

        // 应用之前讨论过的全局视觉修正偏移 (如果还需要的话)
        // 例如: int globalCorrectedX = calculatedX + 15;
        // 例如: int globalCorrectedY = calculatedY - 20;
        // 如果您之前的“原地跳”和“位置错误”问题已通过其他方式解决，
        // 并且棋子能准确落在格子的标准居中位置，那么可能不再需要这个全局修正。
        // 这里我们先假设之前的全局修正是需要的：
        int globalCorrectedX = calculatedX + 35;
        int globalCorrectedY = calculatedY + 35;


        // 应用针对不同玩家的视觉微调偏移
        int finalX = globalCorrectedX;
        int finalY = globalCorrectedY;

        if (playerName.equals("Player 1")) {
            finalX -= PLAYER_VISUAL_OFFSET_X; // 玩家1 向左移动一点
            finalY += PLAYER_VISUAL_OFFSET_Y; // 玩家1 向下移动一点 (视觉上偏左下)
        } else if (playerName.equals("Player 2")) {
            finalX += PLAYER_VISUAL_OFFSET_X; // 玩家2 向右移动一点
            finalY -= PLAYER_VISUAL_OFFSET_Y; // 玩家2 向上移动一点 (视觉上偏右上)
        }
        // 注意: 如果是单人模式，playerName 可能是 "Player 1"，那么它会应用P1的偏移。
        // 如果不希望单人模式有偏移，可以在这里加一个判断，或者确保单人模式时不传递特定玩家名。

        return new int[]{finalX, finalY};
    }


    private static HBox makeColorRow(ImageView playerPieceToUpdate, Color initialPlayerColor) {
        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER);
        List<Rectangle> colorRectanglesInRow = new ArrayList<>(); // This list is captured by the lambda below
        Color[] availableColors = { Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE };
        String[] colorFileNames = { "Red", "Yellow", "Green", "Blue" };

        for (int i = 0; i < availableColors.length; i++) {
            final Color currentColor = availableColors[i]; // currentColor is effectively final for the lambda
            final String colorFileNameSuffix = colorFileNames[i]; // colorFileNameSuffix is effectively final

            Rectangle colorRect = new Rectangle(20, 20, currentColor);
            // colorRectanglesInRow.add(colorRect); // This line was here. If needed by lambda, this is fine.

            // For the lambda below to use colorRectanglesInRow, it must be final or effectively final.
            // It is effectively final as its reference doesn't change after this loop.
            // However, if add was inside loop and used by lambda, better to make colorRectanglesInRow final.
            // Let's declare it final to be safe, though it's likely already effectively final.
            // final List<Rectangle> finalColorRectanglesInRow = colorRectanglesInRow; // Or just use colorRectanglesInRow if it's effectively final

            if (currentColor.equals(initialPlayerColor)) {
                colorRect.setStroke(Color.WHITE);
                colorRect.setStrokeWidth(3);
            } else {
                colorRect.setStroke(Color.WHITE);
                colorRect.setStrokeWidth(1);
            }

            // This lambda captures: playerPieceToUpdate, colorFileNameSuffix, colorRectanglesInRow (or finalColorRectanglesInRow), colorRect
            // playerPieceToUpdate is a parameter, so it's effectively final for this lambda.
            // colorRect is local to the loop iteration but re-declared each time, the one captured is the one for that iteration.
            colorRect.setOnMouseClicked(event -> {
                String pieceImagePath = "/assets/piece" + colorFileNameSuffix + ".png";
                Image newPieceImage = loadImage(pieceImagePath);
                if (newPieceImage != null) {
                    playerPieceToUpdate.setImage(newPieceImage);
                }
                // If colorRectanglesInRow was populated inside this loop and you need all rects:
                // The current 'colorRectanglesInRow' would be the list from the outer scope.
                // If 'colorRectanglesInRow' was declared inside 'makeColorRow' and populated in the loop,
                // then by the time this lambda executes, the list will contain all rectangles from the loop.
                // The code `colorRectanglesInRow.add(colorRect)` should be BEFORE `colorRect.setOnMouseClicked`
                // if the intent is for the lambda to iterate over the complete list from *this* call to makeColorRow.

                // The current logic of 'colorRectanglesInRow' being used in lambda might be to update strokes of *all* rects in the current row.
                // This requires colorRectanglesInRow to be populated before this lambda is defined for all rects.
                // The way it is structured (add happens before this click listener for *this specific rect*),
                // it seems `colorRectanglesInRow` should be populated with ALL rects of this row.
                // So, the `colorRectanglesInRow.add(colorRect);` should ideally be placed such that the list is complete
                // or the lambda correctly refers to the list that contains all sibling rectangles.

                // Safest: use a new list for each row if `makeColorRow` is called multiple times.
                // The current code where `colorRectanglesInRow` is instantiated in `makeColorRow` means each call gets a fresh list.
                // And this list is populated within its `for` loop.
                // The lambda for each `colorRect` will capture the `colorRectanglesInRow` list specific to its `makeColorRow` invocation.
                // This is correct. The variable `colorRectanglesInRow` is effectively final within the scope of the lambda.
                // Its contents are modified (adding rects), but the reference to the list itself is not changed after initialization.

                for (Rectangle rectInRow : colorRectanglesInRow) { // Iterates over the list specific to this row
                    rectInRow.setStroke(Color.WHITE);
                    rectInRow.setStrokeWidth(rectInRow == colorRect ? 3 : 1); // Highlight the clicked one
                }
            });
            colorRectanglesInRow.add(colorRect); // Add after setting listener, or before; here it means list is built up
            row.getChildren().add(colorRect);
        }
        return row;
    }
}
