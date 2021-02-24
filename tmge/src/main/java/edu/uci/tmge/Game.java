package edu.uci.tmge;

public interface Game {
  void launch();
  void pause();
  void resume();
  void quit();
  String getName();
  double getScore();
}
