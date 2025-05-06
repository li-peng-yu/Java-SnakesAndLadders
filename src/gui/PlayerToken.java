package gui;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlayerToken {
    private final ImageView avatar;
    private int currentPosition = 1;

    private static final int CELL_SIZE = 60;
    private static final int SIZE = 10;

    public PlayerToken(String avatarPath) {
        avatar = new ImageView(new Image(PlayerToken.class.getResource("/assets/player1_avatar.png").toExternalForm()));
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
    }

    public ImageView getAvatarNode() {
        return avatar;
    }

    /**
     * 由外部逻辑层调用，让棋子移动到指定位置（带动画）
     * @param position 新的格子编号 1-100
     */
    public void moveToPosition(int position) {
        if (position < 1 || position > 100) return;

        int[] newPos = getCoordinates(position);

        TranslateTransition transition = new TranslateTransition(Duration.millis(500), avatar);
        transition.setToX(newPos[0]);
        transition.setToY(newPos[1]);
        transition.play();

        currentPosition = position;
    }

    private int[] getCoordinates(int position) {
        int row = (position - 1) / SIZE;
        int col = (position - 1) % SIZE;

        if (row % 2 == 0) {
            col = SIZE - 1 - col;
        }

        int x = col * CELL_SIZE + CELL_SIZE / 2 - 20;
        int y = (SIZE - 1 - row) * CELL_SIZE + CELL_SIZE / 2 - 20;

        return new int[]{x, y};
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
