package edu.uci.puzzlebobble;


import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TileShootingAnimation extends AnimationTimer {

  private static final double VELOCITY = 15.0;
  private final PuzzleBobbleTile tile;
  private final BooleanProperty stopped;
  private double deltaX;
  private double deltaY;
  private double angle;

  public TileShootingAnimation(final PuzzleBobbleTile tile, final double initialAngle) {
    this.tile = tile;
    angle = initialAngle;
    deltaX = getDeltaX();
    deltaY = getDeltaY();
    stopped = new SimpleBooleanProperty(false);
  }

  public BooleanProperty stoppedProperty() {
    return stopped;
  }

  @Override
  public void handle(long now) {
    tile.setX(tile.getX() + deltaX);
    tile.setY(tile.getY() + deltaY);

    // TODO use board dimensions for checking collisions with edges
    if (tile.getX() - tile.getRadius() <= 0.0 ||
        tile.getX() + tile.getRadius() >= 789.0)
    {
      angle = -angle - Math.PI;
      deltaX = getDeltaX();
      deltaY = getDeltaY();
    }

    // TODO check for collisions with other tiles, not just top of board
    if (tile.getY() - tile.getRadius() <= 0.0) {
      stop();
      stopped.set(true);
    }
  }

  private double getDeltaX() {
    return Math.cos(angle) * VELOCITY;
  }

  private double getDeltaY() {
    return Math.sin(angle) * VELOCITY;
  }
}
