package edu.uci.tmge;

public interface GameFactory {
  Game create(String playerName, int playerNumber);
}
