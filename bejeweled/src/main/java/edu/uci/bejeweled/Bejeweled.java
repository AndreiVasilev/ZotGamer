package edu.uci.bejeweled;

import edu.uci.tmge.Game;

public class Bejeweled implements Game {

  private final BejeweledBoard board;

  public Bejeweled() {
    board = new BejeweledBoard();
  }

  @Override
  public void launch() {

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
  public BejeweledBoard getBoard() {
    return board;
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
