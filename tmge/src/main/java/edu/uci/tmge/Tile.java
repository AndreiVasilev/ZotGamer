package edu.uci.tmge;

public class Tile {

  private double x;
  private double y;
  private int type;

  public Tile(double x, double y, int type) {
    this.x = x;
    this.y = y;
    this.type = type;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
