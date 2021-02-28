package edu.uci.bejeweled;


import edu.uci.tmge.Pausable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;

public class BejeweledViewController extends HBox implements Pausable {

  @FXML private GridPane tileGrid;
  @FXML private Label playerLabel;
  @FXML private Label scoreLabel;

  public BejeweledViewController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BejeweledView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    initializeTileGrid();
  }

  private void initializeTileGrid() {
    tileGrid.getRowConstraints().clear();
    tileGrid.getColumnConstraints().clear();

    // TODO get num rows/cols from board
    for (int i = 0; i < 8; ++i) {
      final RowConstraints row = new RowConstraints(65);
      final ColumnConstraints col = new ColumnConstraints(65);
      tileGrid.getRowConstraints().add(row);
      tileGrid.getColumnConstraints().add(col);
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
