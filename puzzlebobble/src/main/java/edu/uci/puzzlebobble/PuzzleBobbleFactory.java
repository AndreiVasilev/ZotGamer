package edu.uci.puzzlebobble;

import edu.uci.tmge.GameFactory;

public class PuzzleBobbleFactory implements GameFactory {
  @Override
  public PuzzleBobble create(String playerName) {
    return new PuzzleBobble(playerName);
  }
}
