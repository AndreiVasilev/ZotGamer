package edu.uci.puzzlebobble;

import edu.uci.tmge.Tile;

public class PuzzleBobbleTile extends Tile {

    private static final double speed = 1.0;
    private double angle;

    PuzzleBobbleTile(int x, int y, int type){
        super(x, y, type);
        // int 0 blue
        // int 1 red
        // int 2 yellow
        // int 3 teal,
        // int 4 pink,
        // int 5 green,
        // int 6 white
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public static double getSpeed() {
        return speed;
    }

    // no setSpeed bc its final
}
