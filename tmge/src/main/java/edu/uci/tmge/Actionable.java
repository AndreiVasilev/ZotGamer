package edu.uci.tmge;

public interface Actionable {
  void addAction(GameEvent actionTrigger, Runnable action);
  void removeAction(GameEvent actionTrigger, Runnable action);
}
