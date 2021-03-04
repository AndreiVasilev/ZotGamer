package edu.uci.tmge;

import edu.uci.tmge.core.Player;
import edu.uci.tmge.core.PlayerManager;

import java.util.*;

public class Scoreboard {

  private final PlayerManager playerManager;

  public Scoreboard() {
    playerManager = PlayerManager.instance();
  }

  /**
   * Returns a map of game names to lists of scoreboard entries
   * that contain the player name and their score
   */
  public Map<String, List<ScoreboardEntry>> getHighScores() {
    final Map<String, List<ScoreboardEntry>> scoreboard = new HashMap<>();
    final Collection<Player> players = playerManager.getPlayers();

    // Iterate over all players and get all high scores
    for (final Player player : players) {
      final Map<String, Double> highScores = player.getHighScores();

      // Add scoreboard entry for every high score of player
      for (final Map.Entry<String, Double> gameScore : highScores.entrySet()) {
        scoreboard.putIfAbsent(gameScore.getKey(), new ArrayList<>());
        scoreboard.get(gameScore.getKey()).add(new ScoreboardEntry(player.getName(), gameScore.getValue()));
      }
    }

    // Sort all scoreboard entries to display highest scores at top
    for (final List<ScoreboardEntry> entries : scoreboard.values()) {
      Collections.sort(entries);
    }

    return scoreboard;
  }
}
