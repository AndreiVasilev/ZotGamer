package edu.uci.tmge;

import edu.uci.tmge.core.PlayerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLauncher {

    private final Map<String, GameFactory> gameFactories;
    private final PlayerManager manager;

    public GameLauncher() {
        manager = new PlayerManager();
        gameFactories = new HashMap<>();
    }

    public void registerGame(final String name, final GameFactory factory) {
        gameFactories.put(name, factory);
    }

    public void launchGame(final String name, final List<String> players) {
        if (gameFactories.containsKey(name)) {
            final MultiplayerGame multiplayerGame = new MultiplayerGame();
            final GameFactory gameFactory = gameFactories.get(name);

            for (int i = 0; i < players.size(); ++i) {
                final Game game = gameFactory.create(players.get(i), i + 1);

                // TODO create game window interface, game window factory, add game to window, and set window positions

                multiplayerGame.addGame(game);
            }

            multiplayerGame.launch();

            // TODO get scores from games and save to player profiles
            multiplayerGame.addAction(GameEvent.GAME_END, () -> {
                // manager.save();
            });
        }
    }
}
