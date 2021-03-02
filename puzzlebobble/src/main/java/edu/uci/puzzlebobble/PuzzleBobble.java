package edu.uci.puzzlebobble;

import edu.uci.tmge.Game;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PuzzleBobble implements Game {

  private final PuzzleBobbleViewController viewController;
  private final PuzzleBobbleBoard board;
  private final Stage gameWindow;
  private final int player;

  public PuzzleBobble(final int player) {
    this.player = player;
    board = new PuzzleBobbleBoard();
    viewController = new PuzzleBobbleViewController(board);
    gameWindow = new Stage();
    gameWindow.setResizable(false);
    gameWindow.setTitle("Puzzle Bobble - Player " + player);
  }

  @Override
  public void launch() {
    final Scene mainScene = new Scene(viewController);
    gameWindow.setScene(mainScene);
    gameWindow.show();
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
}
