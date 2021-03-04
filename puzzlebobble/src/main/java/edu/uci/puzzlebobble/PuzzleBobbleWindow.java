package edu.uci.puzzlebobble;

import edu.uci.tmge.GameEvent;
import edu.uci.tmge.GameWindow;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PuzzleBobbleWindow implements GameWindow {

  private final Stage gameWindow;

  public PuzzleBobbleWindow(final PuzzleBobble game) {
    gameWindow = new Stage();
    gameWindow.setResizable(false);
    gameWindow.setTitle(game.getGameName() + " - " + game.getPlayerName());
    gameWindow.setOnCloseRequest(event -> game.quit());
    gameWindow.setScene(new Scene(game.getViewController()));
    game.addAction(GameEvent.GAME_END, this::close);
  }

  @Override
  public void show() {
    gameWindow.show();
  }

  @Override
  public void close() {
    gameWindow.close();
  }

  @Override
  public double getScreenX() {
    return gameWindow.getX();
  }

  @Override
  public double getScreenY() {
    return gameWindow.getY();
  }

  @Override
  public void setScreenX(double x) {
    gameWindow.setX(x);
  }

  @Override
  public void setScreenY(double y) {
    gameWindow.setY(y);
  }
}