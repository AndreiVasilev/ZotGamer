package edu.uci.tmge;

public interface Actionable {
  void addAction(GameEvent event, Runnable action);
  void removeAction(GameEvent event, Runnable action);
}
