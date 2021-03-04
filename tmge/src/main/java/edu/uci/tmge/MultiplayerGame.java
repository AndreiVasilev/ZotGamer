package edu.uci.tmge;

import java.util.*;

public class MultiplayerGame implements Pausable, Actionable {

    private final Map<GameEvent, List<Runnable>> eventActions;
    private final List<Game> games;
    private int currentPlayer;

    public MultiplayerGame(){
        eventActions = new HashMap<>();
        games = new ArrayList<>();
        currentPlayer = 0;
    }

    public void addGame(Game game){
        games.add(game);
    }

    public List<Game> getGames() {
        return new ArrayList<>(games);
    }

    public void launch() {
        for (int i = 0; i < games.size(); ++i) {
            final Game game = games.get(i);
            game.addAction(GameEvent.TURN_END, this::switchPlayers);
            game.addAction(GameEvent.GAME_END, this::quit);
            game.launch();
            if (i != currentPlayer) {
                game.pause();
            }
        }

        eventActions.getOrDefault(GameEvent.GAME_START, Collections.emptyList()).forEach(Runnable::run);
    }

    @Override
    public void resume(){
        games.get(currentPlayer).resume();
        eventActions.getOrDefault(GameEvent.GAME_RESUME, Collections.emptyList()).forEach(Runnable::run);
    }

    @Override
    public void pause(){
        games.get(currentPlayer).pause();
        eventActions.getOrDefault(GameEvent.GAME_PAUSE, Collections.emptyList()).forEach(Runnable::run);
    }

    public void quit(){
        for (final Game game : games) {
            if (!game.isOver()) {
                game.quit();
            }
        }

        eventActions.getOrDefault(GameEvent.GAME_END, Collections.emptyList()).forEach(Runnable::run);
    }

    public void switchPlayers(){
        games.get(currentPlayer).pause();
        currentPlayer = (currentPlayer + 1) % games.size();
        games.get(currentPlayer).resume();
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
