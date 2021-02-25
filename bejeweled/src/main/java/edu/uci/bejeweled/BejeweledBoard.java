package edu.uci.bejeweled;

import edu.uci.tmge.Board;
import edu.uci.tmge.Tile;

import java.util.ArrayList;
import java.util.List;

public class BejeweledBoard extends Board {
    private Tile selectTile;
    // there are 4 types of tiles

    // 0: blank
    private List<Tile> matches;
    private List<Tile> selectedTiles; // first tile is first selected, second is second selected

    public BejeweledBoard(){
        super();
        this.width = 8;
        this.height = 8;

    }

    public void initialize() {
        for (int i = 0; i < width; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j <height; j++) {
                tiles.get(i).add(new Tile(i, j, 0));
            }
        }

        setupBaord();
    }

    public boolean hasMatches() {
        return false;
    }

    public void removeMatches() {

    }

    public boolean isGameOver() {
        return false;
    }

    public boolean hasValidMoves() {
        return  false;
    }

    public void swapWithSelected(Tile tile) {

    }

    public ArrayList<ArrayList<Tile>> findGroups() {
        return new ArrayList<ArrayList<Tile>>();
    }

    private void setupBaord() {
        boolean done = false;

        while (!done) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    tiles.get(i).get(j).setType( (int)(Math.random()*7) + 1);
                }
            }
        }

        // remove matches
        removeMatches();


        // check if there are valid moves
        if (hasValidMoves()) {
            done = true;
        }
    }

}
