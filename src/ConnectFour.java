import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ConnectFour extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane canvas = new StackPane();
        GameBoard board = new GameBoard();

        canvas.getChildren().addAll(board, board.chipPane);

        BorderPane primary = new BorderPane(canvas);
        Label status = new Label();
        status.setText("Connect Four");
        primary.setTop(status);

        Scene sc = new Scene(primary);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Connect Four");
        primaryStage.show();
    }
}
