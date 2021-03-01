package edu.uci.tmge;

public interface Game extends Pausable {
  void launch();
  void quit();
  String getName();
  double getScore();
}
