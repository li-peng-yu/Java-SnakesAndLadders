package gui;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gui.TokenAnimator;

public class PlayerToken {
    private final ImageView avatar;
    private int currentPosition = 1;
    // In PlayerToken.java
    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }
    public PlayerToken(String avatarPath, double startX, double startY) {
        this.avatar = new ImageView(new Image(PlayerToken.class.getResource(avatarPath).toExternalForm()));
        avatar.setFitWidth(40);
        avatar.setFitHeight(40);
        avatar.setLayoutX(startX);
        avatar.setLayoutY(startY);
    }

    public ImageView getAvatarNode() {
        return avatar;
    }

    public void moveToPosition(int targetPosition) {
        if (targetPosition < 1 || targetPosition > 100 || targetPosition == currentPosition) return;

        int start = currentPosition;
        int steps = Math.abs(targetPosition - start);
        boolean forward = targetPosition > start;

        new Thread(() -> {
            try {
                for (int i = 1; i <= steps; i++) {
                    int nextPos = forward ? start + i : start - i;
                    int[] from = getCoordinates(currentPosition);
                    int[] to = getCoordinates(nextPos);
                    int finalNextPos = nextPos;
                    Platform.runLater(() -> TokenAnimator.jumpTo(avatar, from[0], from[1], to[0], to[1]));
                    currentPosition = finalNextPos;
                    Thread.sleep(250);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private int[] getCoordinates(int position) {
        final int CELL_SIZE = 60;
        final int SIZE = 10;
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
