package edu.uci.tmge;

public interface GameWindow {
  void show();
  void close();
  boolean isShowing();
  double getScreenX();
  double getScreenY();
  void setScreenX(double x);
  void setScreenY(double y);
  double getWidth();
  double getHeight();
}
