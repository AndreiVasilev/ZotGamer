package edu.uci.bejeweled;

import edu.uci.tmge.Board;
import edu.uci.tmge.Tile;

import java.util.*;
import java.util.stream.Collectors;

public class BejeweledBoard extends Board {

    private BejeweledTile selectedTile;
    private BejeweledGameState state = BejeweledGameState.REMOVE_TILE;
    private boolean gameStarted =  false;
    private int counter = 0;

    public BejeweledBoard(){
        super(8, 8);
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

    public boolean successfulSwap(Tile tile) {
        if (!canSwap(selectedTile, tile)) {
            return false;
        }

        swapTiles(selectedTile, tile);
        if (!hasMatches()) {
            swapTiles(selectedTile, tile);
            return false;
        }

        return true;
    }

    public void postMoveUpdate() {
        if (counter != 0) {
            counter = (counter + 1) % 50;
            return;
        }
        if (state == BejeweledGameState.REMOVE_TILE) {
            removeTiles();
            state = BejeweledGameState.SHIFT_TILE;
        }
        else if (state == BejeweledGameState.SHIFT_TILE) {
            shiftTiles();
            state = BejeweledGameState.FILL_EMPTY_TILE;
        }
        else {
            fillEmptyTiles();
            state = BejeweledGameState.REMOVE_TILE;
        }
        ++counter;
    }

    @Override
    public void initialize() {
        for (int row = 0; row < height; row++) {
            tiles.add(new ArrayList<>());
            for (int col = 0; col < width; col++) {
                tiles.get(row).add(new BejeweledTile(col, row, 0));
            }
        }

        setupBoard();
        this.gameStarted = true;
    }

    @Override
    public boolean hasMatches() {
        return !findMatches().isEmpty();
    }

    @Override
    public void removeMatches() {
        while(hasMatches()) {
            removeTiles();
            shiftTiles();
            fillEmptyTiles();
        }
    }

    @Override
    public boolean isGameOver() {
        return !hasValidMoves();
    }

    private List<Map<String, Integer>> findMatches() {
        List<Map<String, Integer>> matches = new ArrayList<>();
        int totalMatches = 0;
        //horizontal
        for(int row = 0; row < height; row++) {
            int matchLen = 1;

            for(int col = 0; col < width; col++){
                boolean noMoreMatch = false;
                if(col == this.width - 1) {
                    noMoreMatch = true;
                }
                else {
                    if(tiles.get(row).get(col).getType() == tiles.get(row).get(col+1).getType()){
                        ++matchLen;
                    }
                    else {
                        noMoreMatch = true;
                    }
                }

                if(noMoreMatch) {
                    if(matchLen >= 3) {
                        matches.add(new HashMap<>());
                        matches.get(totalMatches).put("row", row);
                        matches.get(totalMatches).put("col", col+1-matchLen);
                        matches.get(totalMatches).put("hor", 1);
                        matches.get(totalMatches).put("length", matchLen);
                        totalMatches++;
                    }
                    matchLen =1;
                }
            }
        }
        //vertical
        for(int col = 0; col < width; col++) {
            int matchLen = 1;

            for(int row = 0; row<height; row++) {
                boolean noMoreMatch = false;
                if(row == this.height-1) {
                    noMoreMatch = true;
                }
                else {
                    if(tiles.get(row).get(col).getType() == tiles.get(row+1).get(col).getType()){
                        ++matchLen;
                    }
                    else {
                        noMoreMatch = true;
                    }
                }
                if(noMoreMatch) {
                    if(matchLen >= 3) {
                        matches.add(new HashMap<>());
                        matches.get(totalMatches).put("col", col);
                        matches.get(totalMatches).put("row", row+1-matchLen);
                        matches.get(totalMatches).put("hor", 0);
                        matches.get(totalMatches).put("length", matchLen);
                        totalMatches++;
                    }
                    matchLen =1;
                }

            }
        }
        return matches;
    }

    private double calculateScore(List<Map<String, Integer>> matches){
        double score = 0;
        for (final Map<String, Integer> match : matches){
            score += 100*(match.get("length")-2);
        }
        return score;
    }

    private void removeTiles() {
        List<Map<String, Integer>> matches = findMatches();
        if(gameStarted){
            this.setScore(this.getScore()+calculateScore(matches));
        }
        for (final Map<String, Integer> match : matches) {
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


    private void fillEmptyTiles() {
        for(final List<Tile> row : tiles) {
            for(final Tile tile : row) {
                if(tile.getType() == 0) {
                    tile.setType((int)(Math.random()*7) + 1);
                }
            }
        }
    }

    private void shiftTiles() {
        List<Map<String, Integer>> matches = findMatches();
        for (final Map<String, Integer> match : matches) {
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
    private boolean hasValidMoves() {
        Tile tile;
        for (int row = 0; row < height-1; row++) {
            for (int col = 0; col < width-1; col++) {
                tile = tiles.get(row).get(col);
                swapTiles(tile, tiles.get(row+1).get(col));
                if (hasMatches()){
                    tile = tiles.get(row).get(col);
                    swapTiles(tile, tiles.get(row+1).get(col)); // swap back
                    return true;
                }
                else {
                    tile = tiles.get(row).get(col);
                    swapTiles(tile, tiles.get(row+1).get(col)); //swap back
                }

                tile = tiles.get(row).get(col);
                swapTiles(tile, tiles.get(row).get(col+1));
                if (hasMatches()){
                    tile = tiles.get(row).get(col);
                    swapTiles(tile, tiles.get(row).get(col+1)); // swap back
                    return true;
                }
                else {
                    tile = tiles.get(row).get(col);
                    swapTiles(tile, tiles.get(row).get(col+1)); // swap back
                }

            }
        }
        return false;
    }

    private boolean canSwap(final Tile tile1, final Tile tile2) {
        final int xDiff = Math.abs(tile1.getX() - tile2.getX());
        final int yDiff = Math.abs(tile1.getY() - tile2.getY());
        return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
    }

    private void swapTiles(final Tile tile1, final Tile tile2) {
        if ((tile2.getX() == width) || (tile2.getY() == height)) {
            return;
        }

        final int type2 = tile2.getType();
        tile2.setType(tile1.getType());
        tile1.setType(type2);
    }

    private void setupBoard() {
        boolean done = false;

        while (!done) {
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    tiles.get(row).get(col).setType( (int)(Math.random()*7) + 1);
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
