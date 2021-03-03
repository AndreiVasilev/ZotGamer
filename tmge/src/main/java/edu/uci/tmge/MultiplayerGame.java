package edu.uci.tmge;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerGame {

    private final List<Game> games;
    private int currentPlayer;

    public MultiplayerGame(){
        this.games = new ArrayList<>();
        currentPlayer = 0;
    }

    public void addGame(Game game){
        games.add(game);
    }

    public void launch() {
        for (int i = 0; i < games.size(); ++i) {
            final Game game = games.get(i);
            game.addEndOfTurnAction(this::switchPlayers);
            game.launch();
            if (i != currentPlayer) {
                game.pause();
            }
        }
    }

    public void resume(){
        games.get(currentPlayer).resume();
    }

    public void pause(){
        games.get(currentPlayer).pause();
    }

    public void quit(){
        for (final Game game : games) {
            game.quit();
        }
    }

    public void switchPlayers(){
        games.get(currentPlayer).pause();
        currentPlayer = (currentPlayer + 1) % games.size();
        games.get(currentPlayer).resume();
    }
}
