import javafx.animation.FadeTransition;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Slot extends Circle {
    public boolean isEmpty = true;
    public int col;
    public int row;
    public Color fill;
    Slot(int c, int r) {
        super(50, Color.WHITE);
        this.col = c;
        this.row = r;
    }
}
