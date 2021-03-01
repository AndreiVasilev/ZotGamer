package edu.uci.bejeweled;

import edu.uci.tmge.Game;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bejeweled implements Game {

  private final BejeweledBoard board;

  public Bejeweled() {
    board = new BejeweledBoard();
  }

  @Override
  public void launch() {
    board.initialize();

    final Stage stage = new Stage();
    final Scene mainScene = new Scene(new BejeweledViewController(board));
    stage.setTitle("Bejeweled - Player 1");
    stage.setScene(mainScene);
    stage.setResizable(false);
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
