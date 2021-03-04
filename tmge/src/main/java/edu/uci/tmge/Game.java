package edu.uci.tmge;

public interface Game extends Pausable {
  void launch();
  void quit();
  double getScore();
  boolean isOver();
  String getGameName();
  String getPlayerName();
  void addEndOfTurnAction(Runnable action);
  void removeEndOfTurnAction(Runnable action);
  void addEndOfGameAction(Runnable action);
  void removeEndOfGameAction(Runnable action);
}
