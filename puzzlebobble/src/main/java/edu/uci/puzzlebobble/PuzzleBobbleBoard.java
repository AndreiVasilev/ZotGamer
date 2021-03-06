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
    private static final int X_OFFSET = 20;
    private static final int Y_OFFSET = 20;
    private List<Tile> matches;
    private final int tileHeight;
    private final int TILE_WIDTH;
    private final int ROW_HEIGHT;
    private final int rowOffset;
    private final int boardWidth;
    private final int boardHeight;
    private PuzzleBobbleTile currentShotTile;


    public PuzzleBobbleBoard(){
        super(15,14 ); //will be calculated later
        boardHeight = 620;
        boardWidth = 620;
        tileHeight = 40;
        TILE_WIDTH = 40;
        ROW_HEIGHT = 34;
        rowOffset = 0;
        //windowHeight and windowWidth will be calculated in the init
        //tiles does not to be instatiated bc it is called in board? correct? feel free to
        //ping me on disc bc Id like to learn, you can del afterwards- car
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    @Override
    public void initialize() {
        for (int row = 0; row < height; row++) {
            tiles.add(new ArrayList<>());
            for (int col = 0; col < width; col++) {
                tiles.get(row).add(new PuzzleBobbleTile(col, row, 0, 0));
            }
        }
        setupBoard();
    }

    @Override
    public boolean hasMatches() {
        return false;
    }

    @Override
    public void removeMatches() {
        final List<PuzzleBobbleTile> matchingTiles = this.findGroups(this.currentShotTile);

        if (matchingTiles.size() > 2) {
            this.setTilesToEmpty(matchingTiles);
        }

        final List<PuzzleBobbleTile> floatingTiles = this.findFloatingTiles();
        this.setTilesToEmpty(floatingTiles);

        // updateScore(List, "flaoting") + calcScore("matching") {
        //           this.score  +=  list.size() *
        //
        // }
    }

    @Override
    public boolean isGameOver() {
        // true - if entire board is .type -1 [y,x]
        /*
        *   for row in < height ++
        *      for col in < width ++
        *              row, col
        *
        * */

        // this.height - 1 == last row index
        // if theres anything in that ^ last row that is not -1 , then return true

        return false;
    }

    public boolean hasValidMoves() {
        return  false;
    }

    public Collection<PuzzleBobbleTile> getTiles() {
        return tiles.stream().flatMap(Collection::stream)
            .map(tile -> (PuzzleBobbleTile)tile)
            .collect(Collectors.toList());
    }

    public void setTilesToEmpty(List<PuzzleBobbleTile> arrOfTiles){
        for (PuzzleBobbleTile tile: arrOfTiles){
            tile.setType(-1);
        }
    }

    public List<PuzzleBobbleTile> findFloatingTiles(){
        final Queue<PuzzleBobbleTile> tilesToCheck = new LinkedList<>();
        boolean[][] visited = new boolean[this.height][this.width];

        // Find starting point for search
        for (final Tile tile : tiles.get(0)) {
            if (tile.getType() != -1) {
                tilesToCheck.add((PuzzleBobbleTile) tile);
                visited[tile.getY()][tile.getX()] = true;
            }
        }

        // Breadth first search tiles connected to starting point
        while (!tilesToCheck.isEmpty()) {
            final PuzzleBobbleTile tile = tilesToCheck.remove();
            final List<PuzzleBobbleTile> neighbors = getNeighbors(tile);
            for (final PuzzleBobbleTile neighbor : neighbors) {
                if (neighbor.getType() != -1 && !visited[neighbor.getY()][neighbor.getX()]) {
                    visited[neighbor.getY()][neighbor.getX()] = true;
                    tilesToCheck.add(neighbor);
                }
            }
        }

        // Any tiles not reachable from starting point are floating
        return getTiles().stream()
            .filter(tile -> tile.getType() != -1 && !visited[tile.getY()][tile.getX()])
            .collect(Collectors.toList());
    }

    // breath first search for neighboring matching tiles
    public ArrayList<PuzzleBobbleTile> findGroups(PuzzleBobbleTile startingTile) {
        // initialize a 2D array to check if visited each cell
        boolean[][] visited = new boolean[this.height][this.width];

        // initializing queue that'll hold neighbors of the same .type to further inspect
        LinkedList<PuzzleBobbleTile> queueOfTilesToProcess = new LinkedList<PuzzleBobbleTile>();
        queueOfTilesToProcess.add(startingTile);

        // initializing list that'll hold the matching tiles
        ArrayList<PuzzleBobbleTile> matchingGroups = new ArrayList<>();

        // search for neighbors
        while(queueOfTilesToProcess.size() > 0){
            // remove Tile at the front of the queue
            PuzzleBobbleTile curTile = queueOfTilesToProcess.remove();

            // skip current Tile if it is empty OR has been visited
            if (visited[curTile.getY()][curTile.getX()]) {
                continue;
            }

            matchingGroups.add(curTile);

            ArrayList<PuzzleBobbleTile> neighbors = getNeighbors(curTile);

            for(PuzzleBobbleTile neighbor : neighbors){
                // if a neighbor has not been visited of the currentTile
                if (neighbor.getType() == startingTile.getType() && !visited[neighbor.getY()][neighbor.getX()]){
                    // then add to queue to process it's color and further neighbors
                    queueOfTilesToProcess.add(neighbor);
                }
            }

            // set the current Tile as 'true' in visited array
            visited[curTile.getY()][curTile.getX()] = true;
        }

        return matchingGroups;
    }

    private ArrayList<PuzzleBobbleTile> getNeighbors(PuzzleBobbleTile aTile){
        // Neighbor offset tables
        int[][][] neighborOffsets = {
                // Event row offsets
                {{1, 0}, {1, 1}, {0, 1}, {-1, 0}, {0, -1}, {-1, 1}},
                /// Odd row offsets
                {{1, 0}, {0, 1}, {1, -1}, {-1, 0}, {-1, -1}, {0, -1}},
        };

        int[][] correspondingOffsets = neighborOffsets[ aTile.getY() % 2 ];

        ArrayList<PuzzleBobbleTile> neighbors = new ArrayList<PuzzleBobbleTile>();

        for (int i = 0; i < correspondingOffsets.length; i++){
            // a neighbor's coordinates
            int nbY = aTile.getY() + correspondingOffsets[i][0];
            int nbX = aTile.getX() + correspondingOffsets[i][1];

            // Ensure the neighbor tile is within bounds of the board
            // before storing as a neighbor
            if (nbX >= 0 && nbX < width && nbY >= 0 && nbY < height && tiles.get(nbY).get(nbX).getType() != -1 ){
                neighbors.add( (PuzzleBobbleTile)tiles.get(nbY).get(nbX) );
            }
        }
        return neighbors;
    }

    private void setupBoard() {
        //boolean done = false;

        for (int row = 0; row < height; row++) {
            int randomTile = (int)(Math.random() * 6) + 1;
            int count = 0;
            for (int col = 0; col < width; col++) {
                if (count >=2){

                    int newTile = (int)(Math.random() * 7);
                    //if random is STILL the same, force a diff color
                    if (newTile == randomTile)
                        newTile = (newTile + 1) % 6; // there are 7 colors, 6 bc zero counts
                    //forces no more than 2 same color per row
                    randomTile = newTile;
                }
                count++;

                if (row < height/2) // fill half the height
                    tiles.get(row).get(col).setType(randomTile);
                else
                    tiles.get(row).get(col).setType(-1);
            }
        }
    }

    public boolean isCollided(final PuzzleBobbleTile shotTile) {
        this.currentShotTile = shotTile;

        final Collection<PuzzleBobbleTile> tiles = getTiles();
        for (final PuzzleBobbleTile tile : tiles) {
            if (tile.getType() < 0) {
                continue;
            }

            if (intersecting(tile, shotTile)) {
                    snapTile(shotTile);
                return true;
            }
        }
        return false;
    }

    // TODO check to see not snapping onto non-empty tile
    public void snapTile(PuzzleBobbleTile shotTile) {
        final double adjustedX = shotTile.getVisualX() + TILE_WIDTH / 2.0;
        final double adjustedY = shotTile.getVisualY() + TILE_WIDTH / 2.0;
        int row = getYGridPosition(adjustedY);
        int col = getXGridPosition(adjustedX, adjustedY);
        shotTile.setVisualX(getXTileCoordinates(col, row));
        shotTile.setVisualY(getYTileCoordinates(row));
        shotTile.setX(col);
        shotTile.setY(row);
        this.tiles.get(row).set(col, shotTile);
    }

    public double getXTileCoordinates(int column, int row) {
        int tileX = X_OFFSET + column * TILE_WIDTH;
        if ((row + rowOffset) % 2 == 0)
            tileX += TILE_WIDTH / 2;
        return tileX;
    }

    public double getYTileCoordinates(int row) {
        return Y_OFFSET + row * ROW_HEIGHT;
    }

    public int getXGridPosition(double x, double y){
        final int yPos = (int) Math.floor((y - Y_OFFSET) / ROW_HEIGHT);

        int xOffset = 0;
        if ((yPos + rowOffset) % 2 == 0)
            xOffset = TILE_WIDTH / 2;

        return (int) Math.floor(((x - xOffset) - X_OFFSET) / TILE_WIDTH);
    }

    public int getYGridPosition(double y){
        return (int) Math.floor((y - Y_OFFSET) / ROW_HEIGHT);
    }

    public boolean intersecting(final PuzzleBobbleTile tile1, final PuzzleBobbleTile tile2) {
        final double dx = tile1.getVisualX() - tile2.getVisualX();
        final double dy = tile1.getVisualY() - tile2.getVisualY();
        final double dist = Math.sqrt(dx * dx + dy * dy);
        return dist < tile1.getRadius() * 2.0;
    }
}