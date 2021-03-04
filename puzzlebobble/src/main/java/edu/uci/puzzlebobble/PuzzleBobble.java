package edu.uci.puzzlebobble;

import edu.uci.tmge.Game;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

public class PuzzleBobble implements Game {

  private final PuzzleBobbleViewController viewController;
  private final PuzzleBobbleBoard board;
  private final List<Runnable> endOfTurnActions;
  private final List<Runnable> endOfGameActions;
  private final Stage gameWindow;
  private final int player;

  public PuzzleBobble(final int player) {
    this.player = player;

    board = new PuzzleBobbleBoard();
    board.initialize();

    viewController = new PuzzleBobbleViewController(board);

    gameWindow = new Stage();
    gameWindow.setResizable(false);
    gameWindow.setTitle("Puzzle Bobble - Player " + player);
    gameWindow.setOnCloseRequest(event -> quit());

    endOfTurnActions = new ArrayList<>();
    endOfGameActions = new ArrayList<>();
  }

  @Override
  public void launch() {
    final Scene mainScene = new Scene(viewController);
    gameWindow.setOnCloseRequest(event -> quit());
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
    endOfGameActions.forEach(Runnable::run);
    gameWindow.close();
  }

  @Override
  public String getName() {
    return "Puzzle Bobble";
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
