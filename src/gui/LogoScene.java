// LogoScene.java
package gui;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class LogoScene {

    public static Scene getLogoScene(Runnable onFinish) {
        ImageView logo = new ImageView(new Image("file:assets/logo.png"));
        logo.setFitWidth(300);
        logo.setPreserveRatio(true);

        StackPane root = new StackPane(logo);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 800, 600);

        // 淡出动画
        FadeTransition ft = new FadeTransition(Duration.seconds(3), logo);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setDelay(Duration.seconds(2));
        ft.setOnFinished(e -> onFinish.run());
        ft.play();

        return scene;
    }
}
