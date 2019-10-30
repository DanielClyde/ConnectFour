import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


public class GameBoard extends GridPane {
    public Slot[][] slots;
    public SimpleBooleanProperty playerOneTurn = new SimpleBooleanProperty(true);
    public Pane chipPane;
    public Pane winPane;
    public Color winner;

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
        this.winPane = new Pane();
        this.winPane.setPickOnBounds(false);
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

                        Color fill = playerOneTurn.getValue() ? Color.RED : Color.BLUE;
                        this.dropChip(availableSlot, fill);
                        availableSlot.isEmpty = false;
                        availableSlot.fill = fill;
                        this.checkForWinner(availableSlot);
                        this.playerOneTurn.set(!playerOneTurn.getValue());
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

    public void checkForWinner(Slot slot) {
        // vertical
        Slot[] colOfSlots = this.slots[slot.col];
        int count = 0;
        for(Slot s : colOfSlots) {
            if (s.fill == slot.fill) {
                count++;
            } else {
                count = 0;
            }
            if (count >= 4) {
                System.out.println("winner!");
                this.winner = slot.fill;
                this.showWinPane();
            }
        }

        // horizontal
        count = 0;
        for (Slot[] slotCol : this.slots) {
            for (Slot s : slotCol) {
                if (s.row == slot.row) {
                    if (s.fill == slot.fill) {
                        count++;
                    } else {
                        count = 0;
                    }
                    if (count >= 4) {
                        System.out.println("Winner!");
                        this.winner = slot.fill;
                        this.showWinPane();
                    }
                }
            }
        }

        //diagonal
        this.checkDiagonal(slot);
    }

    public void checkDiagonal(Slot slot) {
        int sum = 1;
        try {
            Slot temp = this.slots[slot.col + 1][slot.row + 1];
            while (temp.fill == slot.fill) {
                sum++;
                this.checkSum(sum, slot);
                temp = this.slots[temp.col + 1][temp.row + 1];
            }
            temp = this.slots[slot.col - 1][slot.row - 1];
            while (temp.fill == slot.fill) {
                sum++;
                this.checkSum(sum, slot);
                temp = this.slots[temp.col - 1][temp.row - 1];
            }
            sum = 1;
            temp = this.slots[slot.col - 1][slot.row + 1];
            while (temp.fill == slot.fill) {
                sum++;
                this.checkSum(sum, slot);
                temp = this.slots[temp.col - 1][temp.row + 1];
            }
            temp = this.slots[slot.col + 1][slot.row - 1];
            while (temp.fill == slot.fill) {
                sum++;
                this.checkSum(sum, slot);
                temp = this.slots[temp.col + 1][temp.row - 1];
            }
        } catch (Exception ex) {

        }
    }

    public void checkSum(int sum, Slot s) {
        if(sum >= 4) {
            this.winner = s.fill;
            System.out.println("Diagonal Winner!");
            this.showWinPane();
        }
    }

    private void showWinPane() {
        String winningText = this.winner == Color.RED ? "Red Wins!" : this.winner == Color.BLUE ? "Blue Wins!" : "TIE";
        Text txt = new Text(winningText);
        txt.setFont(new Font(150));
        txt.setFill(this.winner);
        txt.setStroke(Color.BLACK);
        txt.setRotate(-25);
        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setX(this.chipPane.getWidth() / 5 - 50);
        txt.setY(this.chipPane.getHeight() / 2);
        Effect glow = new Glow(5);
        txt.setEffect(glow);

        this.winPane.getChildren().add(txt);
        this.winPane.setPickOnBounds(true);
        FadeTransition fade = new FadeTransition();
        fade.setNode(txt);
        fade.setAutoReverse(true);
        fade.setCycleCount(10);
        fade.setDuration(Duration.millis(2000));
        fade.setToValue(0.1);
        fade.setFromValue(0.99);
        fade.play();

    }
}
