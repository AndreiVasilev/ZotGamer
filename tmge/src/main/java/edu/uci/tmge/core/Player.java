package edu.uci.tmge.core;


import java.util.HashMap;
import java.util.Map;

public class Player {

  private final String name;
  private final Map<String, Double> highScores;

  public Player(String name) {
    this.name = name;
    this.highScores = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void addHighScore(final String gameName, final double score) {
    highScores.put(gameName, score);
  }

  public Map<String, Double> getHighScores() {
    return new HashMap<>(highScores);
  }

  public double getHighScore(final String gameName) {
    if (highScores.containsKey(gameName)) {
      return highScores.get(gameName);
    }
    return 0.0;
  }
}
