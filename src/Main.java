import gui.LogoScene;
import gui.LoginScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Snakes and Ladders");
        primaryStage.setScene(LogoScene.getLogoScene(() -> {
            primaryStage.setScene(LoginScene.getLoginScene(primaryStage));
        }));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}