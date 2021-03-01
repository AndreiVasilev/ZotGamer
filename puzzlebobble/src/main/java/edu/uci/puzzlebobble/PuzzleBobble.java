package edu.uci.puzzlebobble;

import edu.uci.tmge.Game;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PuzzleBobble implements Game {

  private final PuzzleBobbleBoard board;

  public PuzzleBobble() {
    board = new PuzzleBobbleBoard();
  }

  @Override
  public void launch() {
    final Stage stage = new Stage();
    final Scene mainScene = new Scene(new PuzzleBobbleViewController(board));
    stage.setResizable(false);
    stage.setTitle("Zot Gamer");
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
