import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameBoard extends GridPane {
    public ArrayList<Slot> slots;

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
                Slot s = new Slot();
                this.slots.add(s);
                add(s, i, j);
            }
        }
    }
}
