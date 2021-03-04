package edu.uci.puzzlebobble;

import edu.uci.tmge.Game;
import edu.uci.tmge.GameFactory;

public class PuzzleBobbleFactory implements GameFactory {
  @Override
  public Game create(String playerName) {
    return new PuzzleBobble(playerName);
  }
}
