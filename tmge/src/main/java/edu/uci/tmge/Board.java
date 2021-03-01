package edu.uci.tmge;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

  protected final List<List<Tile>> tiles;
  protected final int width;
  protected final int height;
  protected double score;

  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    tiles = new ArrayList<>();
    score = 0;
  }

  public abstract void initialize();

  public abstract boolean hasMatches();

  public abstract void removeMatches();

  public abstract boolean isGameOver();

  public void setScore(double score) {
    this.score = score;
  }

  public double getScore() {
    return score;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
