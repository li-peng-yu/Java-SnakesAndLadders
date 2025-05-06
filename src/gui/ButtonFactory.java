package gui;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * 美观统一按钮工厂类
 */
public class ButtonFactory {

    public static Button mainButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefSize(300, 40);
        button.setFont(Font.font("Verdana", 14));
        button.setStyle(
                "-fx-background-color: #a5d6a7;" +         // 柔和绿色
                        "-fx-text-fill: #2e7d32;" +               // 深绿字体
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 4, 0.3, 0, 2);"
        );
        button.setOnAction(e -> action.run());
        return button;
    }

    public static Button ghostButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setPrefSize(300, 40);
        button.setFont(Font.font("Verdana", 14));
        button.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: #607d8b;" +            // 蓝灰边框
                        "-fx-text-fill: #607d8b;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 2, 0.2, 0, 1);"
        );
        button.setOnAction(e -> action.run());
        return button;
    }
}
