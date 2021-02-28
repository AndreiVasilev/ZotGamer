package edu.uci.puzzlebobble;


import edu.uci.tmge.Game;

public class PuzzleBobble implements Game {

  private final PuzzleBobbleBoard board;

  public PuzzleBobble() {
    board = new PuzzleBobbleBoard();
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
  public PuzzleBobbleBoard getBoard() {
    return board;
  }

  @Override
  public String getName() {
    return "Puzzle Bobble";
  }

  @Override
  public double getScore() {
    return 0;
  }
}
