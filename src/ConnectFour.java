import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConnectFour extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameBoard board = new GameBoard();

        Scene sc = new Scene(board);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Connect Four");
        primaryStage.show();
    }
}
