package edu.uci.main;

public enum GameType {
  BEJEWELED {
    @Override
    public String toString() {
      return "Bejeweled";
    }
  },
  PUZZLE_BOBBLE {
    @Override
    public String toString() {
      return "Puzzle Bobble";
    }
  }
}
