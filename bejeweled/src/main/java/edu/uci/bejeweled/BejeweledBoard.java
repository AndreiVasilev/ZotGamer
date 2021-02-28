package edu.uci.bejeweled;

import edu.uci.tmge.Board;
import edu.uci.tmge.Tile;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class BejeweledBoard extends Board {
    private Tile selectTile;
    // there are 8 types of tiles

    // 0: blank
    private List<Dictionary<String, Integer>> matches;
    private List<Tile> selectedTiles; // first tile is first selected, second is second selected

    public BejeweledBoard(){
        super();
        this.width = 8;
        this.height = 8;
        this.matches = new ArrayList<>();
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

    public void findMatches() {
        this.matches.clear();
        int totalMatches = 0;
        //horizontal
        for(int i = 0; i<height; i++) {
            int matchLen = 1;

            for(int j = 0; j<width; j++){
                boolean noMoreMatch = false;
                if(j == this.width - 1) {
                    noMoreMatch = true;
                }
                else {
                    if(tiles.get(i).get(j).getType() == tiles.get(i).get(j+1).getType()){
                        ++matchLen;
                    }
                    else {
                        noMoreMatch = true;
                    }
                }

                if(noMoreMatch) {
                    if(matchLen >= 3) {
                        this.matches.add(new Hashtable<String, Integer>());
                        this.matches.get(totalMatches).put("row", i);
                        this.matches.get(totalMatches).put("col", j+1-matchLen);
                        this.matches.get(totalMatches).put("hor", 1);
                        this.matches.get(totalMatches).put("length", matchLen);
                        totalMatches++;
                    }
                    matchLen =1;
                }
            }
        }
        //vertical
        for(int i = 0; i<width; i++) {
            int matchLen = 1;

            for(int j = 0; j<height; j++) {
                boolean noMoreMatch = false;
                if(j == this.height-1) {
                    noMoreMatch = true;
                }
                else {
                    if(tiles.get(j).get(i).getType() == tiles.get(j+1).get(i).getType()){
                        ++matchLen;

                    }
                    else {
                        noMoreMatch = true;
                    }
                }
                if(noMoreMatch) {
                    if(matchLen >= 3) {
                        this.matches.add(new Hashtable<String, Integer>());
                        this.matches.get(totalMatches).put("col", i);
                        this.matches.get(totalMatches).put("row", j+1-matchLen);
                        this.matches.get(totalMatches).put("hor", 0);
                        this.matches.get(totalMatches).put("length", matchLen);
                        totalMatches++;
                    }
                    matchLen =1;
                }

            }
        }


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

    //TODO: finish removeMatches
    @Override
    public void removeMatches() {
        this.findMatches();

        while(this.matches.size()>0) {
            this.removeTiles();

            this.shiftTiles();

            this.fillEmptyTiles();

            this.findMatches();
        }
    }

    //Finds all matches in the board and sets it to 0
    public void removeTiles() {
        for(int i = 0; i<this.matches.size();++i) {
            int matchLen = this.matches.get(i).get("length");
            int row = this.matches.get(i).get("row");
            int col = this.matches.get(i).get("col");
            int offset = 0;
            for(int j = 0; j<matchLen;++j) {
                if(this.matches.get(i).get("hor") == 1){
                    this.tiles.get(row).get(col+offset).setType(0);
                    offset++;
                }
                else {
                    this.tiles.get(row+offset).get(col).setType(0);
                    offset++;
                }
            }
        }
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    public void fillEmptyTiles() {
        for(int i = 0; i<this.width; ++i) {
            for(int j = 0; j<this.height; ++j) {
                if(this.tiles.get(i).get(j).getType() == 0) {
                    this.tiles.get(i).get(j).setType((int)(Math.random()*7) + 1);
                }
            }
        }

    }


    public void shiftTiles() {
        for(int i = 0; i<this.matches.size();++i) {
            int matchLen = this.matches.get(i).get("length");
            int row = this.matches.get(i).get("row");
            int col = this.matches.get(i).get("col");
            //horizontal
            if(this.matches.get(i).get("hor") == 1) {
                for(int r=row-1; r>=0; --r) {
                    for(int c = col; c<col+matchLen; c++) {
                        this.tiles.get(r+1).get(c).setType(this.tiles.get(r).get(c).getType());
                        this.tiles.get(r).get(c).setType(0);
                    }
                }

            }
            //vertical
            else {
                for(int r = row-1; r>=0; --r) {
                    for(int k = r; k<r+matchLen;++k) {
                        this.tiles.get(k+1).get(col).setType(this.tiles.get(k).get(col).getType());
                        this.tiles.get(k).get(col).setType(0);
                    }
                }
            }

        }

    }

    //find moves
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
        int currX = (int) selectTile.getX();
        int currY = (int) selectTile.getY();
        int swapX = (int) swapTile.getX();
        int swapY = (int) swapTile.getY();

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

    public void printBoard(){
        for(int i = 0; i<width; ++i){
            for(int j = 0; j<height; ++i){
                System.out.print(tiles.get(i).get(j).getType() + " ");
            }
            System.out.println();
        }
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
