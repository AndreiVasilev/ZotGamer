package edu.uci.tmge;

public interface Game extends Pausable {
  void launch();
  void quit();
  String getName();
  double getScore();
  boolean isOver();
  void addEndOfTurnAction(Runnable action);
  void removeEndOfTurnAction(Runnable action);
  void addEndOfGameAction(Runnable action);
  void removeEndOfGameAction(Runnable action);
}
