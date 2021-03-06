package edu.uci.tmge.core;

import edu.uci.tmge.*;

import java.util.*;

public class MultiplayerGame implements Pausable, Actionable {

    private final Map<GameEvent, List<Runnable>> eventActions;
    private final List<GameWindow> gameWindows;
    private final List<Game> games;
    private int currentPlayer;

    public MultiplayerGame(){
        eventActions = new HashMap<>();
        gameWindows = new ArrayList<>();
        games = new ArrayList<>();
        currentPlayer = 0;
    }

    public void addGame(Game game, GameWindow gameWindow){
        games.add(game);
        gameWindows.add(gameWindow);
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

            final GameWindow window = gameWindows.get(i);
            window.show();
            window.setScreenX(window.getScreenX() - 500.0 + i * window.getWidth());

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

        for (final GameWindow window : gameWindows) {
            if (window.isShowing()) {
                window.close();
            }
        }

        if (games.stream().allMatch(Game::isOver)) {
            eventActions.getOrDefault(GameEvent.GAME_END, Collections.emptyList()).forEach(Runnable::run);
        }
    }

    public void switchPlayers(){
        final int player = currentPlayer;
        games.get(currentPlayer).pause();
        currentPlayer = getNextPlayer();

        while (currentPlayer != player) {
            if (!games.get(currentPlayer).isOver()) {
                games.get(currentPlayer).resume();
                break;
            }
            currentPlayer = getNextPlayer();
        }
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

    private int getNextPlayer() {
        return (currentPlayer + 1) % games.size();
    }
}
