package edu.uci.tmge;

public class ScoreboardEntry implements Comparable<ScoreboardEntry> {

  private final String playerName;
  private final double score;

  public ScoreboardEntry(String playerName, double score) {
    this.playerName = playerName;
    this.score = score;
  }

  public String getPlayerName() {
    return playerName;
  }

  public double getScore() {
    return score;
  }

  @Override
  public int compareTo(ScoreboardEntry o) {
    return Double.compare(o.score, score);
  }
}
