package edu.uci.puzzlebobble;


import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;

public class TileShootingAnimation extends AnimationTimer {

  private static final double VELOCITY = 20.0;
  private final PuzzleBobbleBoard board;
  private final PuzzleBobbleTile tile;
  private final BooleanProperty stopped;
  private double deltaX;
  private double deltaY;
  private double angle;

  public TileShootingAnimation(final PuzzleBobbleBoard board, final PuzzleBobbleTile tile, final double initialAngle) {
    this.board = board;
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
    tile.setVisualX(tile.getVisualX() + deltaX);
    tile.setVisualY(tile.getVisualY() + deltaY);

    if (tile.getVisualX() - tile.getRadius() <= 0.0 ||
        tile.getVisualX() + tile.getRadius() >= board.getVisualWidth())
    {
      angle = -angle - Math.PI;
      deltaX = getDeltaX();
      deltaY = getDeltaY();
    }

    if (board.isCollided(tile)) {
      board.removeMatches();
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
