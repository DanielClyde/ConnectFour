import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.EventListener;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;

public class ConnectFour extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane canvas = new StackPane();
        GameBoard board = new GameBoard();

        canvas.getChildren().addAll(board, board.chipPane, board.winPane);

        BorderPane primary = new BorderPane();
        Label status = new Label();
        String statusTxt = board.playerOneTurn.getValue() ? "Red's turn" : "Blue's turn";
        status.setText(statusTxt);
        status.setFont(new Font(28));
        primary.setAlignment(status, Pos.TOP_CENTER);
        primary.setTop(status);
        primary.setCenter(canvas);

        board.playerOneTurn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                String txt = newValue ? "Red's turn!" : "Blue's turn!";
                if (board.winner != null) {
                    txt = "Game Over!";
                }
                status.setText(txt);
            }
        });

        Scene sc = new Scene(primary);
        primaryStage.setScene(sc);
        primaryStage.setTitle("Connect Four");
        primaryStage.show();
    }
}
