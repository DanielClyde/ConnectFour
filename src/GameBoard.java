import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameBoard extends GridPane {
    public ArrayList<Slot> slots;
    public boolean playerOneTurn = true;

    GameBoard() {
        BackgroundFill fill = new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY);
        this.setBackground(new Background(fill));
        this.setPadding(new Insets(10));
        this.setVgap(10);
        this.setHgap(10);
        this.slots = new ArrayList<Slot>();
        this.initializeSlots();
    }

    public void initializeSlots() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                Slot s = new Slot(i, j);
                this.slots.add(s);
                add(s, i, j);
                s.setOnMouseClicked(event -> {
                    if (s.isEmpty) {
                        System.out.println(s.col);
                        Color fill = playerOneTurn ? Color.RED : Color.BLUE;
                        this.dropChip(s.col, s.row, fill);
                        s.setFill(fill);
                        s.isEmpty = false;
                        this.playerOneTurn = !playerOneTurn;
                    } else {
                        System.out.println("Can't go there!");
                    }
                });
            }
        }
    }

    public void dropChip(int column, int row, Color c) {
        Circle chip = new Circle(50, c);

    }
}
