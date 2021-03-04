package edu.uci.bejeweled;

import edu.uci.tmge.Game;
import edu.uci.tmge.GameWindowFactory;

public class BejeweledWindowFactory implements GameWindowFactory {
  @Override
  public BejeweledWindow create(Game game) {
    return new BejeweledWindow((Bejeweled) game);
  }
}
