package edu.uci.bejeweled;


import edu.uci.tmge.Pausable;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

    this.bejeweledBoard = bejeweledBoard;
    initializeTileGrid();
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

    drawTileGrid();
  }

  private void drawTileGrid() {
    tileGrid.getChildren().clear();

    final Collection<BejeweledTile> tiles = bejeweledBoard.getTiles();
    for (final BejeweledTile tile : tiles) {
      tile.getVisualTile().setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          if (bejeweledBoard.getSelectedTile().isPresent()) {
            final BejeweledTile selected = bejeweledBoard.getSelectedTile().get();

            if (bejeweledBoard.canSwap(selected, tile)) {
              bejeweledBoard.swapTiles(selected, tile);

              if (!bejeweledBoard.hasMatches()) {
                bejeweledBoard.swapTiles(selected, tile);
              }

              final AnimationTimer animationTimer = new AnimationTimer() {

                private int state = 0;
                private int counter = 0;

                @Override
                public void handle(long now) {
                  if(bejeweledBoard.hasMatches()) {
                    if (counter != 0) {
                      counter = (counter + 1) % 50;
                      return;
                    }
                    if (state == 0)
                      bejeweledBoard.removeTiles();
                    else if (state == 1)
                      bejeweledBoard.shiftTiles();
                    else
                      bejeweledBoard.fillEmptyTiles();
                    state = (state + 1) % 3;
                    ++counter;
                    drawTileGrid();
                  } else {
                    stop();
                    bejeweledBoard.resetSelectedTile();
                  }
                }
              };

              animationTimer.start();
              return;
            }
          }

          bejeweledBoard.resetSelectedTile();
          bejeweledBoard.selectTile(tile);
        }
      });
      tileGrid.add(tile.getVisualTile(), (int) tile.getX(), (int) tile.getY());
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
