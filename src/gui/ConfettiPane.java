package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfettiPane extends Pane {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private static final int SPAWN_INTERVAL_MS = 30;
    private static final int CONFETTI_PER_SPAWN = 5;
    private static final double GRAVITY = 0.05;
    private static final double WIND_CHANGE_FREQ = 0.015;

    // 新增：定义像素化彩带的尺寸
    private static final int PIXEL_WIDTH = 8;  // 像素块的宽度
    private static final int PIXEL_HEIGHT = 8; // 像素块的高度 (可以和宽度一样，使其成为正方形)

    private final List<Confetti> confettiList = new ArrayList<>();
    private final Random random = new Random();
    private long lastSpawnTime = 0;
    private final Canvas canvas = new Canvas(WIDTH, HEIGHT);

    public ConfettiPane(Stage primaryStage) {
        getChildren().add(canvas);
        Button retryBtn = new Button("One More Time");
        retryBtn.setPrefWidth(260);
        retryBtn.setLayoutX((WIDTH - 240) / 2.0);
        retryBtn.setLayoutY(HEIGHT * 0.65);
        retryBtn.setFont(Font.loadFont(
                LoginScene.class.getResource("/assets/pixel_font.ttf").toExternalForm(), 24));
        retryBtn.setStyle(
                        "-fx-background-radius:0; " +
                        "-fx-background-color:#4a90e2; " +
                        "-fx-text-fill:white;"
        );
        retryBtn.setOnAction(e -> {
            primaryStage.setScene(ModeSelectScene.getModeSelectScene(primaryStage));
        });
        getChildren().add(retryBtn);
        startAnimation();
    }

    private void startAnimation() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastSpawnTime > SPAWN_INTERVAL_MS) {
                    spawnConfetti();
                    lastSpawnTime = currentTime;
                }
                updateConfetti();
                render(gc);
            }
        };
        timer.start();
    }

    private void spawnConfetti() {
        double spawnY = HEIGHT * 0.3;
        for (int i = 0; i < CONFETTI_PER_SPAWN; i++) {
            double initialVy = -(random.nextDouble() * 4 + 4);
            confettiList.add(new Confetti(0, spawnY, random.nextDouble() * 6 + 3, initialVy));
            confettiList.add(new Confetti(WIDTH, spawnY, -(random.nextDouble() * 6 + 3), initialVy));
        }
    }

    private void updateConfetti() {
        List<Confetti> toRemove = new ArrayList<>();
        for (Confetti confetti : confettiList) {
            confetti.vy += GRAVITY;
            confetti.vx *= 0.99;
            confetti.vx += (random.nextDouble() - 0.5) * WIND_CHANGE_FREQ;
            confetti.x += confetti.vx;
            confetti.y += confetti.vy;
            confetti.opacity -= 0.002;

            if (confetti.y > HEIGHT + PIXEL_HEIGHT) {
                toRemove.add(confetti);
            }
        }
        confettiList.removeAll(toRemove);
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        for (Confetti confetti : confettiList) {
            gc.save();
            gc.setGlobalAlpha(confetti.opacity);
            gc.translate(confetti.x, confetti.y);
            gc.setFill(confetti.color);
            // 使用 fillRect 绘制像素块
            gc.fillRect(-PIXEL_WIDTH / 2.0, -PIXEL_HEIGHT / 2.0, PIXEL_WIDTH, PIXEL_HEIGHT);
            gc.restore();
        }
    }

    private class Confetti {
        double x, y;
        double vx, vy;
        double opacity = 1.0;
        Color color;

        Confetti(double startX, double startY, double initialVx, double initialVy) {
            this.x = startX;
            this.y = startY;
            this.vx = initialVx;
            this.vy = initialVy;
            this.color = randomColor();
        }

        private Color randomColor() {
            Color[] palette = new Color[]{
                    Color.web("#FF6B6B"),
                    Color.web("#FF8E8E"),
                    Color.web("#FFB6C1"),
                    Color.web("#FFC0CB"),
                    Color.web("#FF7F7F")
            };
            return palette[random.nextInt(palette.length)];
        }
    }
}