package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerToken {
    private final ImageView avatar;
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
}
