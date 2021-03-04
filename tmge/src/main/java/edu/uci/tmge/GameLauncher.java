package edu.uci.tmge;

import edu.uci.tmge.core.MultiplayerGame;
import edu.uci.tmge.core.PlayerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLauncher {

    private final Map<String, GameWindowFactory> gameWindowFactories;
    private final Map<String, GameFactory> gameFactories;
    private final PlayerManager playerManager;

    public GameLauncher() {
        gameWindowFactories = new HashMap<>();
        gameFactories = new HashMap<>();
        playerManager = PlayerManager.instance();
    }

    public void registerGame(final String name, final GameFactory gameFactory, final GameWindowFactory windowFactory) {
        gameFactories.put(name, gameFactory);
        gameWindowFactories.put(name, windowFactory);
    }

    public void launchGame(final String name, final List<String> players) {
        if (gameFactories.containsKey(name)) {
            final GameWindowFactory windowFactory = gameWindowFactories.get(name);
            final GameFactory gameFactory = gameFactories.get(name);
            final MultiplayerGame multiplayerGame = new MultiplayerGame();

            for (final String player : players) {
                final Game game = gameFactory.create(player);
                final GameWindow gameWindow = windowFactory.create(game);
                multiplayerGame.addGame(game, gameWindow);
            }

            multiplayerGame.launch();

            multiplayerGame.addAction(GameEvent.GAME_END, () -> {
                final List<Game> games = multiplayerGame.getGames();
                for (final Game game : games) {
                    final String player = game.getPlayerName();
                    final double score = game.getScore();
                    playerManager.saveScore(player, game.getGameName(), score);
                }
                playerManager.save();
            });
        }
    }
}
