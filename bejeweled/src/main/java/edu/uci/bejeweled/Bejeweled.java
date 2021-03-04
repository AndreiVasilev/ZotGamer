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
  private final int player;

  public Bejeweled(final int player) {
    this.player = player;
    board = new BejeweledBoard();
    board.initialize();
    viewController = new BejeweledViewController(board);
    endOfTurnActions = new ArrayList<>();
  }

  @Override
  public void launch() {
    final Stage stage = new Stage();
    final Scene mainScene = new Scene(viewController);
    stage.setTitle("Bejeweled - Player " + player);
    stage.setScene(mainScene);
    stage.setResizable(false);
    stage.show();
    viewController.isTurnOver().addListener((observable, oldValue, newValue) ->
        endOfTurnActions.forEach(Runnable::run));
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

  }

  @Override
  public String getName() {
    return "";
  }

  @Override
  public double getScore() {
    return board.getScore();
  }

  @Override
  public void addEndOfTurnAction(Runnable runnable) {
    endOfTurnActions.add(runnable);
  }

  @Override
  public void removeEndOfTurnAction(Runnable runnable) {
    endOfTurnActions.remove(runnable);
  }
}
