package edu.uci.tmge;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerGame implements Game {

    private final List<Game> games;
    private int currentPlayer;

    public MultiplayerGame(){
        this.games = new ArrayList<>();
        currentPlayer = 0;
    }

    public void addGame(Game game){
        games.add(game);
    }

    @Override
    public void launch(){

    }

    @Override
    public void  resume(){

    }

    @Override
    public void pause(){

    }

    @Override
    public void quit(){

    }

    @Override
    public String getName(){
        return "";
    }

    @Override
    public double getScore(){
        return 0;
    }

    public void switchPlayers(){
        games.get(currentPlayer).pause();
        currentPlayer = (currentPlayer + 1) % games.size();
        games.get(currentPlayer).resume();
    }

}
