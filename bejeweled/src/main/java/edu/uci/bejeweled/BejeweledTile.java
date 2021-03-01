package edu.uci.bejeweled;

import edu.uci.tmge.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BejeweledTile extends Rectangle {

  private static final Color[] COLORS = {
      Color.WHITE,
      Color.YELLOW,
      Color.AQUA,
      Color.HOTPINK,
      Color.LIGHTGREEN,
      Color.INDIANRED,
      Color.MEDIUMPURPLE
  };

  private final Tile tile;

  public BejeweledTile(final Tile tile) {
    this.tile = tile;
    setFill(COLORS[tile.getType() - 1]);
    setHeight(65.0);
    setWidth(65.0);
    setStroke(Color.BLACK);
    setStrokeWidth(3.0);
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
