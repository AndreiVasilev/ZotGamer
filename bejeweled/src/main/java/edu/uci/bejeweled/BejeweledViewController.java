package edu.uci.bejeweled;


import edu.uci.tmge.Pausable;
import edu.uci.tmge.Tile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.util.Collection;

public class BejeweledViewController extends HBox implements Pausable {

  @FXML private GridPane tileGrid;
  @FXML private Label playerLabel;
  @FXML private Label scoreLabel;
  private final BejeweledBoard bejeweledBoard;

  public BejeweledViewController(final BejeweledBoard bejeweledBoard) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BejeweledView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    initializeTileGrid();
    this.bejeweledBoard = bejeweledBoard;
  }

  private void initializeTileGrid() {
    tileGrid.getRowConstraints().clear();
    tileGrid.getColumnConstraints().clear();

    for (int i = 0; i < bejeweledBoard.getHeight(); ++i) {
      final RowConstraints row = new RowConstraints(65);
      tileGrid.getRowConstraints().add(row);
    }

    for (int i = 0; i < bejeweledBoard.getWidth(); ++i) {
      final ColumnConstraints col = new ColumnConstraints(65);
      tileGrid.getColumnConstraints().add(col);
    }

    final Collection<Tile> tiles = bejeweledBoard.getTiles();
    for (final Tile tile : tiles) {
      final BejeweledTile bejeweledTile = new BejeweledTile(tile);
      tileGrid.add(bejeweledTile, (int) bejeweledTile.getXPos(), (int) bejeweledTile.getYPos());
    }
  }

  @Override
  public void pause() {
    setDisable(true);
  }

  @Override
  public void resume() {
    setDisable(false);
  }
}
