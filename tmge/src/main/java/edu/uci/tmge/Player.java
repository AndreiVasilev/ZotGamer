package edu.uci.tmge;


import java.util.HashMap;
import java.util.Map;

public class Player {

  private String name;
  private final Map<String, Integer> scores;

  public Player(String name) {
    this.name = name;
    this.scores = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
