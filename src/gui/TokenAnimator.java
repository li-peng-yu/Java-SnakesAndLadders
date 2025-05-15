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
        // 使用 Timeline 实现动画
        Timeline jumpTimeline = new Timeline();

        // 定义棋子在动画开始时的 X 和 Y 坐标
        KeyValue kvXStart = new KeyValue(tokenNode.translateXProperty(), tokenNode.getTranslateX());
        KeyValue kvYStart = new KeyValue(tokenNode.translateYProperty(), tokenNode.getTranslateY());

        // 定义棋子在动画中间点（跳跃最高点）的 X 和 Y 坐标
        // X 坐标是起始和结束 X 坐标的中间值
        // Y 坐标是起始和结束 Y 坐标中较小值减去跳跃高度，形成向上跳跃的效果
        KeyValue kvXMid = new KeyValue(tokenNode.translateXProperty(), (tokenNode.getTranslateX() + toX) / 2);
        KeyValue kvYMid = new KeyValue(tokenNode.translateYProperty(), Math.min(tokenNode.getTranslateY(), toY) - JUMP_HEIGHT);

        // 定义棋子在动画结束时的 X 和 Y 坐标
        KeyValue kvXEnd = new KeyValue(tokenNode.translateXProperty(), toX);
        KeyValue kvYEnd = new KeyValue(tokenNode.translateYProperty(), toY);

        // 创建关键帧 (KeyFrame)
        // kfStart: 动画开始时的状态
        // kfMid: 动画中间点（跳跃顶点）的状态，在总时长的一半时到达
        // kfEnd: 动画结束时的状态
        KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvXStart, kvYStart);
        KeyFrame kfMid = new KeyFrame(Duration.millis(DURATION_MS / 2), kvXMid, kvYMid);
        KeyFrame kfEnd = new KeyFrame(Duration.millis(DURATION_MS), kvXEnd, kvYEnd);

        // 将关键帧添加到时间轴
        jumpTimeline.getKeyFrames().addAll(kfStart, kfMid, kfEnd);
        // 播放动画
        jumpTimeline.play();
    }
    /** 新增：直线滑动 */
    public static void slideTo(ImageView tokenNode, double fromX, double fromY, double toX, double toY) {
        Timeline slide = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(tokenNode.translateXProperty(), fromX),
                        new KeyValue(tokenNode.translateYProperty(), fromY)
                ),
                new KeyFrame(Duration.millis(DURATION_MS),
                        new KeyValue(tokenNode.translateXProperty(), toX),
                        new KeyValue(tokenNode.translateYProperty(), toY)
                )
        );
        slide.play();
    }
}