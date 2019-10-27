import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Slot extends Circle {
    public boolean isEmpty = true;
    Slot() {
        super(50, Color.WHITE);
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
