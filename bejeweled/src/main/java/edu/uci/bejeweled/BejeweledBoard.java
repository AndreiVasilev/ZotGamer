package edu.uci.bejeweled;

import edu.uci.tmge.Board;
import edu.uci.tmge.Tile;

import java.util.*;
import java.util.stream.Collectors;

public class BejeweledBoard extends Board {
    private BejeweledTile selectedTile;
    // there are 8 types of tiles

    // 0: blank
    private final List<Map<String, Integer>> matches;
    private List<Tile> selectedTiles; // first tile is first selected, second is second selected

    public BejeweledBoard(){
        super(8, 8);
        this.matches = new ArrayList<>();
        selectedTile = null;
    }

    public Collection<BejeweledTile> getTiles() {
        return tiles.stream().flatMap(Collection::stream)
            .map(tile -> (BejeweledTile)tile)
            .collect(Collectors.toList());
    }

    public Optional<BejeweledTile> getSelectedTile() {
        return Optional.ofNullable(selectedTile);
    }

    public void selectTile(final BejeweledTile tile) {
        tile.select(); // Updates tile color to selected color
        selectedTile = tile;
    }

    public void resetSelectedTile() {
        if (selectedTile != null) {
            selectedTile.unselect(); // Returns selected tile color to normal
        }
        selectedTile = null;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < width; i++) {
            tiles.add(new ArrayList<>());
            for (int j = 0; j <height; j++) {
                tiles.get(i).add(new BejeweledTile(i, j, 0));
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
                        matches.add(new HashMap<>());
                        matches.get(totalMatches).put("row", i);
                        matches.get(totalMatches).put("col", j+1-matchLen);
                        matches.get(totalMatches).put("hor", 1);
                        matches.get(totalMatches).put("length", matchLen);
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
                        matches.add(new HashMap<>());
                        matches.get(totalMatches).put("col", i);
                        matches.get(totalMatches).put("row", j+1-matchLen);
                        matches.get(totalMatches).put("hor", 0);
                        matches.get(totalMatches).put("length", matchLen);
                        totalMatches++;
                    }
                    matchLen =1;
                }

            }
        }
    }

    @Override
    public boolean hasMatches() {
        findMatches();
        return !matches.isEmpty();
    }

    //TODO: finish removeMatches
    @Override
    public void removeMatches() {
        while(hasMatches()) {
            removeTiles();
            shiftTiles();
            fillEmptyTiles();
        }
    }

    //Finds all matches in the board and sets it to 0
    public void removeTiles() {
        for (final Map<String, Integer> match : this.matches) {
            int matchLen = match.get("length");
            int row = match.get("row");
            int col = match.get("col");
            int offset = 0;
            for (int j = 0; j < matchLen; ++j) {
                if (match.get("hor") == 1) {
                    this.tiles.get(row).get(col + offset).setType(0);
                } else {
                    this.tiles.get(row + offset).get(col).setType(0);
                }
                offset++;
            }
        }
    }

    @Override
    public boolean isGameOver() {
        return !hasValidMoves();
    }

    public void fillEmptyTiles() {
        for(final List<Tile> row : tiles) {
            for(final Tile tile : row) {
                if(tile.getType() == 0) {
                    tile.setType((int)(Math.random()*7) + 1);
                }
            }
        }
    }

    public void shiftTiles() {
        for (final Map<String, Integer> match : this.matches) {
            int matchLen = match.get("length");
            int row = match.get("row");
            int col = match.get("col");
            //horizontal
            if (match.get("hor") == 1) {
                for (int r = row - 1; r >= 0; --r) {
                    for (int c = col; c < col + matchLen; c++) {
                        this.tiles.get(r + 1).get(c).setType(this.tiles.get(r).get(c).getType());
                        this.tiles.get(r).get(c).setType(0);
                    }
                }

            }
            //vertical
            else {
                for (int r = row - 1; r >= 0; --r) {
                    for (int k = r; k < r + matchLen; ++k) {
                        this.tiles.get(k + 1).get(col).setType(this.tiles.get(k).get(col).getType());
                        this.tiles.get(k).get(col).setType(0);
                    }
                }
            }

        }

    }

    //find moves
    public boolean hasValidMoves() {
        Tile tile;
        for (int i = 0; i < width-1; i++) {
            for (int j = 0; j < height-1; j++) {
                tile = tiles.get(i).get(j);
                swapTiles(tile, tiles.get(i+1).get(j));
                if (hasMatches()){
                    tile = tiles.get(i).get(j);
                    swapTiles(tile, tiles.get(i+1).get(j)); // swap back
                    return true;
                }

                tile = tiles.get(i).get(j);
                swapTiles(tile, tiles.get(i).get(j+1));
                if (hasMatches()){
                    tile = tiles.get(i).get(j);
                    swapTiles(tile, tiles.get(i).get(j+1)); // swap back
                    return true;
                }

            }
        }
        return false;
    }

    public boolean canSwap(final Tile tile1, final Tile tile2) {
        final int xDiff = (int) Math.abs(tile1.getX() - tile2.getX());
        final int yDiff = (int) Math.abs(tile1.getY() - tile2.getY());
        return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
    }

    public void swapTiles(final Tile tile1, final Tile tile2) {
        if ((tile2.getX() == width) || (tile2.getY() == height)) {
            return;
        }

        // save both tile coordinates
        int tile1X = (int) tile1.getX();
        int tile1Y = (int) tile1.getY();
        int tile2X = (int) tile2.getX();
        int tile2Y = (int) tile2.getY();

        // set initially selected tile coords to be swap tile coords
        tile1.setX(tile2X);
        tile1.setY(tile2Y);

        // set swap tile coords to be initially selected tile coords
        tile2.setX(tile1X);
        tile2.setY(tile1Y);

        // swap tile locations on the board
        tiles.get(tile1X).set(tile1Y, tile2);
        tiles.get(tile2X).set(tile2Y, tile1);
    }

    public void printBoard(){
        for(int i = 0; i < width; ++i){
            for(int j = 0; j < height; ++j){
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
