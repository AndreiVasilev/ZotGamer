package edu.uci.bejeweled;

import edu.uci.tmge.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BejeweledTile extends Rectangle {

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

  public BejeweledTile(final int type) {
    tile = new Tile(0, 0, type);
    setFill(COLORS[type]);
    setHeight(65.0);
    setWidth(65.0);
  }

  public double getXPos() {
    return tile.getX();
  }

  public void setXPos(double x) {
    setX(x);
    tile.setX(x);
  }

  public double getYPos() {
    return tile.getY();
  }

  public void setYPos(double y) {
    setY(y);
    tile.setY(y);
  }
}
