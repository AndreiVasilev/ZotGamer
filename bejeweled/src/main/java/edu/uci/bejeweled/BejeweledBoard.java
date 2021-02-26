package edu.uci.bejeweled;

import edu.uci.tmge.Board;
import edu.uci.tmge.Tile;

import java.util.ArrayList;
import java.util.List;

public class BejeweledBoard extends Board {
    private Tile selectTile;
    // there are 8 types of tiles

    // 0: blank
    private List<Tile> matches;
    private List<Tile> selectedTiles; // first tile is first selected, second is second selected

    public BejeweledBoard(){
        super();
        this.width = 8;
        this.height = 8;

    }

    @Override
    public void initialize() {
        for (int i = 0; i < width; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j <height; j++) {
                tiles.get(i).add(new Tile(i, j, 0));
            }
        }

        setupBoard();
    }

    @Override
    public boolean hasMatches() {
        // horizontal matches
        for (int i = 0; i < width; i++) {
            int matchLen = 0;
            int prevType = 0;
            for (int j = 0; j < height; j++) {
                if (tiles.get(i).get(j).getType()!=prevType) {
                    prevType = tiles.get(i).get(j).getType();
                    matchLen = 0;
                }
                else {
                    matchLen++;
                    if (matchLen >= 3) {
                        return true;
                    }
                }
            }
        }

        // vertical matches
        for (int i = 0; i < width; i++) {
            int matchLen = 0;
            int prevType = 0;
            for (int j = 0; j < height; j++) {
                if (tiles.get(j).get(i).getType()!=prevType) {
                    prevType = tiles.get(j).get(i).getType();
                    matchLen = 0;
                }

                else {
                    matchLen++;
                    if (matchLen >=  3) {
                        return true;
                    }
                }
            }
        }

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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                selectTile = tiles.get(i).get(j);
                swapWithSelected(tiles.get(i+1).get(j));
                if (hasMatches()){
                    selectTile = tiles.get(i).get(j);
                    swapWithSelected(tiles.get(i+1).get(j)); // swap back
                    return true;
                }

                selectTile = tiles.get(i).get(j);
                swapWithSelected(tiles.get(i).get(j+1));
                if (hasMatches()){
                    selectTile = tiles.get(i).get(j);
                    swapWithSelected(tiles.get(i).get(j+1)); // swap back
                    return true;
                }

            }
        }

        return false;
    }

    public void swapWithSelected(Tile swapTile) {
        if ((swapTile.getX() == width) || (swapTile.getY() == height)) {
            return;
        }

        // save both tile coordinates
        int currX = selectTile.getX();
        int currY = selectTile.getY();
        int swapX = swapTile.getX();
        int swapY = swapTile.getY();

        // set initially selected tile coords to be swap tile coords
        selectTile.setX(swapX);
        selectTile.setY(swapY);

        // set swap tile coords to be initially selected tile coords
        swapTile.setX(currX);
        swapTile.setY(currY);

        // swap tile locations on the board
        tiles.get(currX).set(currY, swapTile);
        tiles.get(swapX).set(swapY, selectTile);

    }

    public ArrayList<ArrayList<Tile>> findGroups() {
        return new ArrayList<ArrayList<Tile>>();
    }

    private void setupBoard() {
        boolean done = false;

        while (!done) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    tiles.get(i).get(j).setType( (int)(Math.random()*7) + 1);
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

}
