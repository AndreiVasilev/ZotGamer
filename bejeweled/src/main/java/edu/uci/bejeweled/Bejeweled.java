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
  private final int player;

  public Bejeweled(final int player) {
    this.player = player;

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
    gameWindow.setTitle("Bejeweled - Player " + player);
    gameWindow.setScene(mainScene);
    gameWindow.setResizable(false);
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
    endOfGameActions.forEach(Runnable::run);
    gameWindow.close();
  }

  @Override
  public String getName() {
    return "Bejeweled";
  }

  @Override
  public double getScore() {
    return board.getScore();
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
