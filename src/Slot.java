import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Slot extends Circle {
    public boolean isEmpty = true;
    public int col;
    public int row;
    Slot(int c, int r) {
        super(50, Color.WHITE);
        this.col = c;
        this.row = r;
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
