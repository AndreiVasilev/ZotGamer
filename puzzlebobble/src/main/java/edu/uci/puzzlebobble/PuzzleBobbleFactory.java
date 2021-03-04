package edu.uci.puzzlebobble;

import edu.uci.tmge.Game;
import edu.uci.tmge.GameFactory;

public class PuzzleBobbleFactory implements GameFactory {
  @Override
  public Game create(String playerName, int playerNumber) {
    return new PuzzleBobble(playerName, playerNumber);
  }
}
