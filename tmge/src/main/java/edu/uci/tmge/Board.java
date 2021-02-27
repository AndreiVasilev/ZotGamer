package edu.uci.tmge;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

  protected final List<List<Tile>> tiles;
  protected double score;
  protected int width;
  protected int height;

  public Board() {
    tiles = new ArrayList<>();
    score = 0;
  }

  public abstract void initialize();

  public abstract boolean hasMatches();

  public abstract void removeMatches();

  public abstract boolean isGameOver();
}
