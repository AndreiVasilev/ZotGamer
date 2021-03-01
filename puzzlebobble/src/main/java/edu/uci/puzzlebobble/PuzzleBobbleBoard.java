package edu.uci.puzzlebobble;

import edu.uci.tmge.Board;
//change to PBtile

import edu.uci.tmge.Tile;

import java.util.ArrayList;
import java.util.List;

public class PuzzleBobbleBoard extends Board {
    private Tile selectTile;

    //change to PBtile
    private List<Tile> matches;
    private List<Tile> selectedTiles;

    public PuzzleBobbleBoard(){
        super(8, 10);
    }

    @Override
    public void initialize() {
        for (int i = 0; i < width; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < height; j++) {
                tiles.get(i).add(new Tile(i, j, 0));
                //change to PBTile
            }

        }
    }

    @Override
    public boolean hasMatches() {
        return false;
    }

    @Override
    public void removeMatches() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

            }

        }
    }

    @Override
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

    private void setupBoard() {
        boolean done = false;

        while (!done) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    tiles.get(i).get(j).setType( (int)(Math.random()*6) + 1);
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

    public ArrayList<Integer> getTileCoordinates(int row, int column){
        //var xpos = column * tile
        //placeholder for now
        return new ArrayList<Integer>();
    }
}