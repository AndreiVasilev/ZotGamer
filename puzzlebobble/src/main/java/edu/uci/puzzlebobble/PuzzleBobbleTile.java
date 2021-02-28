package edu.uci.puzzlebobble;

import edu.uci.tmge.Tile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PuzzleBobbleTile extends Circle {

    private static final Color[] COLORS = {
        Color.BLUE,
        Color.RED,
        Color.YELLOW,
        Color.TEAL,
        Color.PINK,
        Color.GREEN,
        Color.WHITE
    };

    private final Tile tile;

    PuzzleBobbleTile(int type) {
        tile = new Tile(0.0, 0.0, type);
        setFill(COLORS[type]);
        setStroke(Color.valueOf("#464646"));
        setStrokeWidth(1.0);
        setRadius(20.0);
    }

    public double getX() {
        return tile.getX();
    }

    public void setX(double x) {
        setLayoutX(x);
        tile.setX(x);
    }

    public double getY() {
        return tile.getY();
    }

    public void setY(double y) {
        setLayoutY(y);
        tile.setY(y);
    }
}
