package edu.uci.tmge;

public interface Game extends Pausable {
  void launch();
  void quit();
  Board getBoard();
  String getName();
  double getScore();
}
