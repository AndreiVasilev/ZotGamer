package edu.uci.puzzlebobble;

import edu.uci.tmge.Game;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PuzzleBobble implements Game {

  private final PuzzleBobbleBoard board;
  private final int player;

  public PuzzleBobble(final int player) {
    board = new PuzzleBobbleBoard();
    this.player = player;
  }

  @Override
  public void launch() {
    final Stage stage = new Stage();
    final Scene mainScene = new Scene(new PuzzleBobbleViewController(board));
    stage.setResizable(false);
    stage.setTitle("Puzzle Bobble - Player " + player);
    stage.setScene(mainScene);
    stage.show();
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void quit() {

  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public double getScore() {
    return 0;
  }
}
