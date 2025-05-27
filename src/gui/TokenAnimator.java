package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TokenAnimator {

    private static final double JUMP_HEIGHT = 20; // 跳跃高度，可以调整
    private static final double DURATION_MS = 300; // 动画持续时间（毫秒），可以调整

    public static void jumpTo(ImageView tokenNode, double toX, double toY) {
        Timeline jumpTimeline = new Timeline();
        KeyValue kvXStart = new KeyValue(tokenNode.layoutXProperty(), tokenNode.getLayoutX());
        KeyValue kvYStart = new KeyValue(tokenNode.layoutYProperty(), tokenNode.getLayoutY());

        KeyValue kvXMid = new KeyValue(tokenNode.layoutXProperty(), (tokenNode.getLayoutX() + toX) / 2);
        KeyValue kvYMid = new KeyValue(tokenNode.layoutYProperty(), Math.min(tokenNode.getLayoutY(), toY) - JUMP_HEIGHT);

        KeyValue kvXEnd = new KeyValue(tokenNode.layoutXProperty(), toX);
        KeyValue kvYEnd = new KeyValue(tokenNode.layoutYProperty(), toY);

        KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvXStart, kvYStart);
        KeyFrame kfMid = new KeyFrame(Duration.millis(DURATION_MS / 2), kvXMid, kvYMid);
        KeyFrame kfEnd = new KeyFrame(Duration.millis(DURATION_MS), kvXEnd, kvYEnd);

        jumpTimeline.getKeyFrames().addAll(kfStart, kfMid, kfEnd);
        jumpTimeline.play();

    }
    //直线滑动
    public static void slideTo(ImageView tokenNode, double fromX, double fromY, double toX, double toY) {
        Timeline slide = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(tokenNode.layoutXProperty(), fromX),
                        new KeyValue(tokenNode.layoutYProperty(), fromY)
                ),
                new KeyFrame(Duration.millis(DURATION_MS),
                        new KeyValue(tokenNode.layoutXProperty(), toX),
                        new KeyValue(tokenNode.layoutYProperty(), toY)
                )
        );
        slide.play();
    }
}