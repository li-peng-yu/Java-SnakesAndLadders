import gui.LogoScene;
import gui.GameBoardGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snakes and Ladders");

        // 显示Logo页，3秒后加载游戏主界面
        primaryStage.setScene(LogoScene.getLogoScene(() -> {
            primaryStage.setScene(GameBoardGUI.getGameScene());
        }));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
