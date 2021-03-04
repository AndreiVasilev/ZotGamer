package edu.uci.bejeweled;

import edu.uci.tmge.GameFactory;

public class BejeweledFactory implements GameFactory {
  @Override
  public Bejeweled create(String playerName) {
    return new Bejeweled(playerName);
  }
}
