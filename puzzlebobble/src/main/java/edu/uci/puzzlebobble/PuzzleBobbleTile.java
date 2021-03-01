package edu.uci.puzzlebobble;

import edu.uci.tmge.Tile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PuzzleBobbleTile extends Tile {

    private static final Color[] COLORS = {
        Color.BLUE,
        Color.RED,
        Color.YELLOW,
        Color.MEDIUMPURPLE,
        Color.HOTPINK,
        Color.LIMEGREEN,
        Color.AQUA
    };

    private final Circle visualTile;

    public PuzzleBobbleTile(final int type) {
        this(0.0, 0.0, type);
    }

    public PuzzleBobbleTile(final double x, final double y, final int type) {
        super(x, y, type);
        visualTile = new Circle();
        visualTile.setFill(COLORS[type]);
        visualTile.setStroke(Color.valueOf("#464646"));
        visualTile.setStrokeWidth(1.0);
        visualTile.setRadius(20.0);
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        visualTile.setLayoutX(x);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        visualTile.setLayoutY(y);
    }

    public Circle getVisualTile() {
        return visualTile;
    }

    public double getRadius() {
        return visualTile.getRadius();
    }
}
