package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition; // 虽然导入了，但最终未使用 PathTransition 实现跳跃
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo; // 虽然导入了，但最终未使用 PathTransition 实现跳跃
import javafx.scene.shape.MoveTo;       // 虽然导入了，但最终未使用 PathTransition 实现跳跃
import javafx.scene.shape.Path;         // 虽然导入了，但最终未使用 PathTransition 实现跳跃
import javafx.util.Duration;

public class TokenAnimator {

    private static final double JUMP_HEIGHT = 20; // 跳跃高度，可以调整
    private static final double DURATION_MS = 300; // 动画持续时间（毫秒），可以调整

    /**
     * 让棋子以跳跃动画移动到新位置。
     *
     * @param tokenNode 代表玩家棋子的 ImageView。
     * @param fromX     起始 X 坐标。
     * @param fromY     起始 Y 坐标。
     * @param toX       目标 X 坐标。
     * @param toY       目标 Y 坐标。
     */
    public static void jumpTo(ImageView tokenNode, double fromX, double fromY, double toX, double toY) {
        Timeline jumpTimeline = new Timeline();

        // 注意：现在所有 KeyValue 都作用于 layoutXProperty 和 layoutYProperty
        // 动画开始时，读取当前的 layoutX/Y
        KeyValue kvXStart = new KeyValue(tokenNode.layoutXProperty(), tokenNode.getLayoutX());
        KeyValue kvYStart = new KeyValue(tokenNode.layoutYProperty(), tokenNode.getLayoutY());

        // 中间点，X 为起始和目标 layoutX 的中点
        // Y 在起始和目标 layoutY 的较小值基础上减去 JUMP_HEIGHT (向上跳)
        KeyValue kvXMid = new KeyValue(tokenNode.layoutXProperty(), (tokenNode.getLayoutX() + toX) / 2);
        KeyValue kvYMid = new KeyValue(tokenNode.layoutYProperty(), Math.min(tokenNode.getLayoutY(), toY) - JUMP_HEIGHT);

        // 动画结束时，layoutX/Y 达到目标 toX, toY
        KeyValue kvXEnd = new KeyValue(tokenNode.layoutXProperty(), toX);
        KeyValue kvYEnd = new KeyValue(tokenNode.layoutYProperty(), toY);

        KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvXStart, kvYStart);
        KeyFrame kfMid = new KeyFrame(Duration.millis(DURATION_MS / 2), kvXMid, kvYMid);
        KeyFrame kfEnd = new KeyFrame(Duration.millis(DURATION_MS), kvXEnd, kvYEnd);

        jumpTimeline.getKeyFrames().addAll(kfStart, kfMid, kfEnd);
        jumpTimeline.play();
    }
    /** 新增：直线滑动 */
    public static void slideTo(ImageView tokenNode, double fromX, double fromY, double toX, double toY) {
        Timeline slide = new Timeline(
                new KeyFrame(Duration.ZERO,
                        // CHANGE THESE TO LAYOUT PROPERTIES
                        new KeyValue(tokenNode.layoutXProperty(), fromX),
                        new KeyValue(tokenNode.layoutYProperty(), fromY)
                ),
                new KeyFrame(Duration.millis(DURATION_MS),
                        // CHANGE THESE TO LAYOUT PROPERTIES
                        new KeyValue(tokenNode.layoutXProperty(), toX),
                        new KeyValue(tokenNode.layoutYProperty(), toY)
                )
        );
        slide.play();
    }
}