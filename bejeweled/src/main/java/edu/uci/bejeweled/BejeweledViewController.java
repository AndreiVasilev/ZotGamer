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

  @FXML private AnchorPane gameOverScreen;
  @FXML private AnchorPane pauseScreen;
  @FXML private HBox gameFrame;
  @FXML private GridPane tileGrid;
  @FXML private Label playerLabel;
  @FXML private Label scoreLabel;
  @FXML private Label finalScore;
  private final BejeweledBoard board;
  private final BooleanProperty turnOver;
  private final BooleanProperty gameOver;

  public BejeweledViewController(final BejeweledBoard board, final String playerName) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BejeweledView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    this.board = board;
    turnOver = new SimpleBooleanProperty(false);
    gameOver = new SimpleBooleanProperty(false);
    playerLabel.setText("Player - " + playerName);
  }

  public void initializeView() {
    tileGrid.getRowConstraints().clear();
    tileGrid.getColumnConstraints().clear();
    scoreLabel.setText(Integer.toString((int) board.getScore()));
    for (int i = 0; i < board.getHeight(); ++i) {
      final RowConstraints row = new RowConstraints(65);
      tileGrid.getRowConstraints().add(row);
    }

    for (int i = 0; i < board.getWidth(); ++i) {
      final ColumnConstraints col = new ColumnConstraints(65);
      tileGrid.getColumnConstraints().add(col);
    }

    drawTileGrid();
  }

  public BooleanProperty isTurnOver() {
    return turnOver;
  }

  public BooleanProperty isGameOver() {
    return gameOver;
  }

  private void drawTileGrid() {
    tileGrid.getChildren().clear();

    final Collection<BejeweledTile> tiles = board.getTiles();
    for (final BejeweledTile tile : tiles) {
      tile.getVisualTile().setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          final AnimationTimer animationTimer = new AnimationTimer() {


            @Override
            public void handle(long now) {
              if(board.hasMatches()) {
                board.postMoveUpdate();
                drawTileGrid();
              } else {
                stop();

                if (board.isGameOver()) {
                  gameOverScreen.setVisible(true);
                  gameFrame.setDisable(true);
                  gameOver.set(true);
                  finalScore.setText("Final Score : " + (int) board.getScore());
                }

                turnOver.set(true);
                board.resetSelectedTile();
                scoreLabel.setText(Integer.toString((int) board.getScore()));
              }
            }
          };

          if (board.getSelectedTile().isPresent()) {
            if (board.successfulSwap(tile)) {
              animationTimer.start();
            }
            board.resetSelectedTile();
            return;
          }

          board.selectTile(tile);
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
