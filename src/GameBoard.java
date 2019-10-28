import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameBoard extends GridPane {
    public Slot[][] slots;
    public boolean playerOneTurn = true;
    public Pane chipPane;

    GameBoard() {
        BackgroundFill fill = new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY);
        this.setBackground(new Background(fill));
        this.setPadding(new Insets(10));
        this.setVgap(10);
        this.setHgap(10);
        this.chipPane = new Pane();
        this.chipPane.setMinWidth(this.getWidth());
        this.chipPane.setMinHeight(this.getHeight());
        this.chipPane.setPickOnBounds(false);
        this.slots = new Slot[7][5];
        this.initializeSlots();
    }

    public void initializeSlots() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                Slot s = new Slot(i, j);
                this.slots[i][j] = s;
                add(s, i, j);
                s.setOnMouseClicked(event -> {
                    if (s.isEmpty) {
                        Slot availableSlot = this.findLowestAvailableSlot(s);

                        Color fill = playerOneTurn ? Color.RED : Color.BLUE;
                        this.dropChip(availableSlot, fill);
                        availableSlot.isEmpty = false;
                        this.checkForWinner();
                        this.playerOneTurn = !playerOneTurn;
                    } else {
                        System.out.println("Can't go there!");
                    }
                });
            }
        }
    }

    public void dropChip(Slot s, Color c) {
        double x = 60;
        for (int i = 0; i < s.col; i++) {
            x += 110;
        }
        double y = 60;
        for (int i = 0; i < s.row; i++) {
            y += 110;
        }

        Circle chip = new Circle(50);
        chip.setFill(c);
        chip.setCenterY(30);
        chip.setCenterX(x);

        Line path = new Line(x, 0, x, y);
        path.setStroke(Color.TRANSPARENT);
        this.chipPane.getChildren().addAll(path,chip);
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(1000));
        pt.setPath(path);
        pt.setNode(chip);
        pt.setCycleCount(1);
        pt.setAutoReverse(false);
        pt.play();
    }

    private Slot findLowestAvailableSlot(Slot s) {
        if (s.row == 4) {
            return s;
        }
        Slot lowerSlot = this.slots[s.col][s.row + 1];
        while (lowerSlot.isEmpty && lowerSlot.row < 4) {
            lowerSlot = this.slots[s.col][lowerSlot.row + 1];
        }
        if (lowerSlot.isEmpty) {
            return lowerSlot;
        } else {
            return this.slots[s.col][lowerSlot.row - 1];
        }
    }

    public void checkForWinner() {

    }
}
