package edu.uci.puzzlebobble;

import edu.uci.tmge.Board;
//change to PBtile

import edu.uci.tmge.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class PuzzleBobbleBoard extends Board {
    private Tile selectTile;

    //change to PBtile
    private List<Tile> matches;
    private List<Tile> selectedTiles;

    public PuzzleBobbleBoard(){
        super();
        this.width = 8;
        this.height = 10;
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

    // breath first search for neighboring matching tiles
    public ArrayList<Tile> findGroups(int tx, int ty) {
        // if resetProcessedFlags() needed -- for loop all && set tiles.processed = false

        Tile startingTile = tiles.get(tx).get(ty);

        // initializing queue that'll hold neighbors of the same .type to further inspect
        LinkedList<Tile> queueOfTilesToProcess = new LinkedList<Tile>();
        queueOfTilesToProcess.add(startingTile);
        // TODO: startingTile.processed = true;

        // init list that'll hold the matching groups
        ArrayList<Tile> matchingGroups = new ArrayList<>();

        // search for neighbors
        while(queueOfTilesToProcess.size() > 0){
            // remove a Tile from queue
            Tile curTile = queueOfTilesToProcess.remove();

            // skip tiles that are empty OR have been processed
            if (curTile.getType() == 0
                // || curTile.processed == true
            ) {
                continue;
            }

            if (curTile.getType() == startingTile.getType()){
                matchingGroups.add(curTile);

                // ArrayList<Tile> neighbors = getNeighbors(curTile.x, curTile.y);

                /*
                    for(Tile t : neighbors){
                        if ( !t.processed ){
                            queueOfTilesToProcess.add(t);
                            t.processed = true;
                        }
                    }
                */
            }
        }

        return matchingGroups;
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