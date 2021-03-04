package edu.uci.bejeweled;

import edu.uci.tmge.Game;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Bejeweled implements Game {

  private final BejeweledViewController viewController;
  private final BejeweledBoard board;
  private final List<Runnable> endOfTurnActions;
  private final List<Runnable> endOfGameActions;
  private final Stage gameWindow;
  private final String playerName;

  public Bejeweled(final String playerName, final int player) {
    this.playerName = playerName;

    board = new BejeweledBoard();
    board.initialize();

    gameWindow = new Stage();
    gameWindow.setResizable(false);
    gameWindow.setTitle("Puzzle Bobble - Player " + player);
    gameWindow.setOnCloseRequest(event -> quit());

    viewController = new BejeweledViewController(board);
    endOfTurnActions = new ArrayList<>();
    endOfGameActions = new ArrayList<>();
  }

  @Override
  public void launch() {
    final Scene mainScene = new Scene(viewController);
    gameWindow.setScene(mainScene);
    gameWindow.show();

    viewController.isTurnOver().addListener((observable, oldValue, turnOver) -> {
      if (turnOver) {
        endOfTurnActions.forEach(Runnable::run);
      }
    });

    viewController.isGameOver().addListener(((observable, oldValue, gameOver) -> {
      if (gameOver) {
        quit();
      }
    }));
  }

  @Override
  public void pause() {
    viewController.pause();
  }

  @Override
  public void resume() {
    viewController.resume();
  }

  @Override
  public void quit() {
    viewController.isGameOver().set(true);
    endOfGameActions.forEach(Runnable::run);
    gameWindow.close();
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String getGameName() {
    return "Bejeweled";
  }

  @Override
  public double getScore() {
    return board.getScore();
  }

  @Override
  public boolean isOver() {
    return board.isGameOver();
  }

  @Override
  public void addEndOfTurnAction(Runnable action) {
    endOfTurnActions.add(action);
  }

  @Override
  public void removeEndOfTurnAction(Runnable action) {
    endOfTurnActions.remove(action);
  }

  @Override
  public void addEndOfGameAction(Runnable action) {
    endOfGameActions.add(action);
  }

  @Override
  public void removeEndOfGameAction(Runnable action) {
    endOfGameActions.remove(action);
  }
}
