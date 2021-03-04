package edu.uci.bejeweled;

import edu.uci.tmge.Game;
import edu.uci.tmge.GameFactory;

public class BejeweledFactory implements GameFactory {
  @Override
  public Game create(String playerName, int playerNumber) {
    return new Bejeweled(playerName, playerNumber);
  }
}
