package edu.uci.bejeweled;

import edu.uci.tmge.Game;
import edu.uci.tmge.GameEvent;

import java.util.*;

public class Bejeweled implements Game {

  private final Map<GameEvent, List<Runnable>> eventActions;
  private final BejeweledViewController viewController;
  private final BejeweledBoard board;
  private final String playerName;

  public Bejeweled(final String playerName) {
    this.playerName = playerName;
    board = new BejeweledBoard();
    viewController = new BejeweledViewController(board);
    eventActions = new HashMap<>();
  }

  public BejeweledViewController getViewController() {
    return viewController;
  }

  @Override
  public void launch() {
    board.initialize();
    viewController.initializeView();

    eventActions.getOrDefault(GameEvent.GAME_START, Collections.emptyList()).forEach(Runnable::run);

    viewController.isTurnOver().addListener((observable, oldValue, turnOver) -> {
      if (turnOver && eventActions.containsKey(GameEvent.TURN_END)) {
        eventActions.get(GameEvent.TURN_END).forEach(Runnable::run);
      }
      else if (!turnOver && eventActions.containsKey(GameEvent.TURN_START)) {
        eventActions.get(GameEvent.TURN_START).forEach(Runnable::run);
      }
    });

    viewController.isGameOver().addListener(((observable, oldValue, gameOver) -> {
      if (gameOver) {
        quit();
      }
    }));
  }

  @Override
  public void pause() {
    viewController.pause();
    eventActions.getOrDefault(GameEvent.GAME_PAUSE, Collections.emptyList()).forEach(Runnable::run);
  }

  @Override
  public void resume() {
    viewController.resume();
    eventActions.getOrDefault(GameEvent.GAME_RESUME, Collections.emptyList()).forEach(Runnable::run);
  }

  @Override
  public void quit() {
    viewController.isGameOver().set(true);
    eventActions.getOrDefault(GameEvent.GAME_END, Collections.emptyList()).forEach(Runnable::run);
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String getGameName() {
    return "Bejeweled";
  }

  @Override
  public double getScore() {
    return board.getScore();
  }

  @Override
  public boolean isOver() {
    return board.isGameOver() || viewController.isGameOver().get();
  }

  @Override
  public void addAction(GameEvent event, Runnable action) {
    eventActions.putIfAbsent(event, new ArrayList<>());
    eventActions.get(event).add(action);
  }

  @Override
  public void removeAction(GameEvent event, Runnable action) {
    if (eventActions.containsKey(event)) {
      eventActions.get(event).remove(action);
    }
  }
}
