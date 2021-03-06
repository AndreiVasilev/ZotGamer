package edu.uci.puzzlebobble;

import edu.uci.tmge.Board;
import edu.uci.tmge.Tile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class PuzzleBobbleBoard extends Board {

    private static final int X_OFFSET = 20;
    private static final int Y_OFFSET = 20;
    private final int TILE_DIAMETER;
    private final int ROW_HEIGHT;
    private final int VISUAL_WIDTH;
    private final int VISUAL_HEIGHT;
    private final int rowOffset;
    private PuzzleBobbleTile currentShotTile;

    public PuzzleBobbleBoard(){
        super(15, 15);
        VISUAL_HEIGHT = 620;
        VISUAL_WIDTH = 620;
        TILE_DIAMETER = 40;
        ROW_HEIGHT = 34;
        rowOffset = 0;
    }

    public int getVisualWidth() {
        return VISUAL_WIDTH;
    }

    public int getVisualHeight() {
        return VISUAL_HEIGHT;
    }

    public PuzzleBobbleTile getRandomTile() {
        return new PuzzleBobbleTile((int) (Math.random() * 7));
    }

    @Override
    public void initialize() {
        for (int row = 0; row < height; row++) {
            tiles.add(new ArrayList<>());
            for (int col = 0; col < width; col++) {
                tiles.get(row).add(new PuzzleBobbleTile(col, row, 0));
            }
        }
        setupBoard();
    }

    @Override
    public boolean hasMatches() {
        return findGroups(currentShotTile).size() > 2;
    }

    @Override
    public void removeMatches() {
        final List<PuzzleBobbleTile> matchingTiles = findGroups(currentShotTile);

        if (matchingTiles.size() > 2) {
            setTilesToEmpty(matchingTiles);
            updateScore(matchingTiles, false);

            final List<PuzzleBobbleTile> floatingTiles = findFloatingTiles();
            setTilesToEmpty(floatingTiles);
            updateScore(floatingTiles, true);
        }
    }

    @Override
    public boolean isGameOver() {
        boolean isEmpty = true;
        boolean outOfBounds = false;
        outerloop:
        //check for all empty tiles
        for (int row = 0; row < height; row ++){
            for (int col = 0; col < width; col++){
                //if there a single tile that is not blank, the game is not over
                if (tiles.get(row).get(col).getType() != -1){
                    isEmpty = false;
                    break outerloop;
                }
            }
        }
        //check last row to see if there is any element placed
        for (int col = 0; col < width; col++){
            //height -1 is last row index
            if (tiles.get((this.height - 1)).get(col).getType() != -1) {
                outOfBounds = true;
                break;
            }
        }

        return isEmpty || outOfBounds;
    }

    public Collection<PuzzleBobbleTile> getTiles() {
        return tiles.stream().flatMap(Collection::stream)
            .map(tile -> (PuzzleBobbleTile)tile)
            .collect(Collectors.toList());
    }

    private void updateScore(List<PuzzleBobbleTile> targetTiles, boolean floating){
        if (floating){
            int floatAmount  = targetTiles.size();
            this.score += 20 * Math.pow(2, floatAmount -1);
        }
        else {
            this.score += (targetTiles.size() * 10);
        }
    }

    private void setTilesToEmpty(List<PuzzleBobbleTile> arrOfTiles){
        for (PuzzleBobbleTile tile: arrOfTiles){
            tile.setType(-1);
        }
    }

    private List<PuzzleBobbleTile> findFloatingTiles(){
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
    private ArrayList<PuzzleBobbleTile> findGroups(PuzzleBobbleTile startingTile) {
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

        for (int[] correspondingOffset : correspondingOffsets) {
            // a neighbor's coordinates
            int nbY = aTile.getY() + correspondingOffset[0];
            int nbX = aTile.getX() + correspondingOffset[1];

            // Ensure the neighbor tile is within bounds of the board
            // before storing as a neighbor
            if (nbX >= 0 && nbX < width && nbY >= 0 && nbY < height
                && tiles.get(nbY).get(nbX).getType() != -1) {
                neighbors.add((PuzzleBobbleTile) tiles.get(nbY).get(nbX));
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
        currentShotTile = shotTile;

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

    private boolean isEmpty(int row, int col){
        return this.tiles.get(row).get(col).getType() == -1;
    }

    public void snapTile(PuzzleBobbleTile shotTile) {
        final double adjustedX = shotTile.getVisualX() + shotTile.getRadius();
        final double adjustedY = shotTile.getVisualY() + shotTile.getRadius();
        int row = getYGridPosition(adjustedY);
        int col = getXGridPosition(adjustedX, adjustedY);

        // adjust Row & Col Based On Borders, ensure idx are valid positions
        if (row >= this.width){
            row = this.width - 1;
        }
        if (row < 0){
            row = 0;
        }
        if (col >= this.height){
            col = this.height - 1;
        }
        if (col < 0){
            col = 0;
        }

        // if current destination slot is NOT empty, search for a lower row with empty column
        if (!isEmpty(row,col)) {
            // Tile is not empty, shift the new tile downwards
            for (int lowerRow = row+1; lowerRow<this.height; lowerRow++) {
                if ( isEmpty(lowerRow, col) ) {
                    row = lowerRow;
                    break;
                }
            }
        }

        shotTile.setVisualX(getXTileCoordinates(col, row));
        shotTile.setVisualY(getYTileCoordinates(row));
        shotTile.setX(col);
        shotTile.setY(row);

        this.tiles.get(row).set(col, shotTile);
    }

    public double getXTileCoordinates(int column, int row) {
        int tileX = X_OFFSET + column * TILE_DIAMETER;
        if ((row + rowOffset) % 2 == 0)
            tileX += TILE_DIAMETER / 2;
        return tileX;
    }

    public double getYTileCoordinates(int row) {
        return Y_OFFSET + row * ROW_HEIGHT;
    }

    private int getXGridPosition(double x, double y){
        final int yPos = (int) Math.floor((y - Y_OFFSET) / ROW_HEIGHT);

        int xOffset = 0;
        if ((yPos + rowOffset) % 2 == 0)
            xOffset = TILE_DIAMETER / 2;

        return (int) Math.floor(((x - xOffset) - X_OFFSET) / TILE_DIAMETER);
    }

    private int getYGridPosition(double y){
        return (int) Math.floor((y - Y_OFFSET) / ROW_HEIGHT);
    }

    private boolean intersecting(final PuzzleBobbleTile tile1, final PuzzleBobbleTile tile2) {
        final double dx = tile1.getVisualX() - tile2.getVisualX();
        final double dy = tile1.getVisualY() - tile2.getVisualY();
        final double dist = Math.sqrt(dx * dx + dy * dy);
        return dist < tile1.getRadius() * 2.0;
    }
}