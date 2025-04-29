import gui.LogoScene;
import gui.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snakes and Ladders");

        // 显示Logo页，3秒后加载登录界面
        primaryStage.setScene(LogoScene.getLogoScene(() -> {
            primaryStage.setScene(LoginScene.getLoginScene(primaryStage));
        }));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
