package edu.uci.tmge;

public interface Game extends Pausable, Actionable {
  void launch();
  void quit();
  double getScore();
  boolean isOver();
  String getGameName();
  String getPlayerName();
}
