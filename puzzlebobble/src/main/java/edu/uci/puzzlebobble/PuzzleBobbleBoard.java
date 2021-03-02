package edu.uci.puzzlebobble;

import edu.uci.tmge.Board;
import edu.uci.tmge.Tile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class PuzzleBobbleBoard extends Board {

    //change to PBtile
    private List<Tile> matches;
    public int x;
    public int y;
    public int radius;
    public int tileHeight;
    public int tileWidth;
    public int windowHeight;
    public int windowWidth;
    public int rowHeight;
    public int rowOffset;


    public PuzzleBobbleBoard(){
        super(15,14 ); //will be calculated later
        this.x = 0;
        this.y = 0;
        this.radius = 10;
        this.tileHeight = 20;
        this.tileWidth = 20;
        this.rowHeight = 34;
        this.rowOffset = 0;
        //windowHeight and windowWidth will be calculated in the init
        //tiles does not to be instatiated bc it is called in board? correct? feel free to
        //ping me on disc bc Id like to learn, you can del afterwards- car
    }

    @Override
    public void initialize() {
        for (int i = 0; i < this.width; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j < height; j++) {
                tiles.get(i).add(new PuzzleBobbleTile(i, j, 0, 0));
            }
        }
        this.windowWidth = this.width * this.tileWidth/2;
        this.windowHeight = (this.height -1) * this.rowHeight + this.tileHeight;

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
        //boolean done = false;

        //while (!done) {
            for (int i = 0; i < this.width; i++) {
                int randomTile = (int)(Math.random() * 6) + 1;
                int count = 0;
                for (int j = 0; j < this.height; j++) {
                    if (count >=2){
                        //change to random tile, we want some variety
                        int newTile = (int)(Math.random() * 7) + 1;
                        //if random is STILL the same, force a diff color
                        if (newTile == randomTile)
                            newTile = (newTile + 1) % 6; // there are 7 colors, 6 bc zero counts
                        //forces no more than 2 same color per row
                        randomTile = newTile;
                    }
                    count++;
                    if (j < this.height/2) // fill half the height
                        this.tiles.get(i).get(j).setType(randomTile);
                    else
                        this.tiles.get(i).get(j).setType(-1);
                }
            }
    }

    public int getXTileCoordinates(int column, int row) {
        int tileX = this.x + column * this.tileWidth;

        if ((row + this.rowOffset) % 2 == 0)
            tileX = this.tileWidth / 2;

        return tileX;
    }
    public int getYTileCoordinates(int row) {
        int tileY = this.y + row * this.rowHeight;

        return tileY;
    }

    public int getXGridPosition(int x){
        int gridY = (int) Math.floor((y-this.y) / this.rowHeight);

        int xOffset = 0;
        if ((gridY + rowOffset) % 2 == 0)
            xOffset = this.tileWidth/2;
        int gridX = (int) Math.floor(((x - xOffset) - this.x) / this.tileWidth);

        return gridX;
    }
    public int getYGridPosition(int x){
        int gridY = (int) Math.floor((y-this.y) / this.rowHeight);
        return gridY;
    }
}