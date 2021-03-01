package edu.uci.bejeweled;

import edu.uci.tmge.Tile;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BejeweledTile extends Tile {

  private static final Color selectedColor = Color.RED;
  private static final Color[] COLORS = {
      Color.BLACK,
      Color.WHITE,
      Color.YELLOW,
      Color.AQUA,
      Color.HOTPINK,
      Color.LIGHTGREEN,
      Color.INDIANRED,
      Color.MEDIUMPURPLE,
  };

  private final Rectangle visualTile;

  public BejeweledTile(final double x, final double y, final int type) {
    super(x, y, type);
    this.visualTile = new Rectangle();
    initializeStyle();
  }

  public Rectangle getVisualTile() {
    return visualTile;
  }

  @Override
  public void setType(int type) {
    super.setType(type);
    setTileColor();
  }

  public void select() {
    visualTile.setFill(selectedColor);
  }

  public void unselect() {
    setTileColor();
  }

  private void initializeStyle() {
    setTileColor();
    visualTile.setHeight(65.0);
    visualTile.setWidth(65.0);
    visualTile.setStroke(Color.BLACK);
    visualTile.setStrokeWidth(3.0);
  }

  private void setTileColor() {
    visualTile.setFill(COLORS[getType()]);
  }
}
