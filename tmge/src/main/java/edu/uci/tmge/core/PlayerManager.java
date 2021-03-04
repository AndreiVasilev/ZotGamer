package edu.uci.tmge.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private static final PlayerManager manager = new PlayerManager();
    private static final String saveLocation = "players.json";
    private final Map<String, Player> players;
    private final Gson gson;

    public static PlayerManager instance() {
        return manager;
    }

    private PlayerManager(){
        gson = new Gson();
        players = load();
    }

    public Player getPlayer(final String playerName) {
        players.putIfAbsent(playerName, new Player(playerName));
        return players.get(playerName);
    }

    public void saveScore(final String playerName, final String gameName, final double score) {
        final Player player = getPlayer(playerName);
        if (player.getHighScore(gameName) < score) {
            player.addHighScore(gameName, score);
        }
    }

    public void save() {
        final String playersJson = gson.toJson(players);
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(saveLocation))) {
            writer.write(playersJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Player> load() {
        try {
            if (! Path.of(saveLocation).toFile().exists()) {
                return new HashMap<>();
            }

            final String playersJson = Files.readString(Path.of(saveLocation));
            final Type type = new TypeToken<HashMap<String, Player>>(){}.getType();
            return gson.fromJson(playersJson, type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

}
