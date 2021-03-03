package edu.uci.bejeweled;


import edu.uci.tmge.Pausable;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Collection;

public class BejeweledViewController extends StackPane implements Pausable {

  @FXML private HBox gameFrame;
  @FXML private AnchorPane pauseScreen;
  @FXML private GridPane tileGrid;
  @FXML private Label playerLabel;
  @FXML private Label scoreLabel;
  private final BejeweledBoard bejeweledBoard;
  private final BooleanProperty turnOver;

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
    turnOver = new SimpleBooleanProperty(false);
    initializeTileGrid();
  }

  public BooleanProperty isTurnOver() {
    return turnOver;
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
          final AnimationTimer animationTimer = new AnimationTimer() {


            @Override
            public void handle(long now) {
              if(bejeweledBoard.hasMatches()) {
                bejeweledBoard.postMoveUpdate();
                drawTileGrid();
              } else {
                stop();
                turnOver.set(true);
                bejeweledBoard.resetSelectedTile();
              }
            }
          };

          if (bejeweledBoard.getSelectedTile().isPresent()) {
            bejeweledBoard.handleSwap(tile);

            animationTimer.start();
            return;

          }

          bejeweledBoard.resetSelectedTile();
          bejeweledBoard.selectTile(tile);
        }
      });
      tileGrid.add(tile.getVisualTile(), tile.getX(), tile.getY());
    }
  }

  @Override
  public void pause() {
    gameFrame.setDisable(true);
    pauseScreen.setVisible(true);
  }

  @Override
  public void resume() {
    gameFrame.setDisable(false);
    pauseScreen.setVisible(false);
    turnOver.set(false);
  }
}
