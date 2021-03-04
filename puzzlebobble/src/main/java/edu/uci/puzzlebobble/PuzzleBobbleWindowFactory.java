package edu.uci.puzzlebobble;

import edu.uci.tmge.Game;
import edu.uci.tmge.GameWindow;
import edu.uci.tmge.GameWindowFactory;

public class PuzzleBobbleWindowFactory implements GameWindowFactory {
  @Override
  public GameWindow create(Game game) {
    return new PuzzleBobbleWindow((PuzzleBobble) game);
  }
}
