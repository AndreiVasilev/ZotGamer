package edu.uci.puzzlebobble;

import edu.uci.tmge.Pausable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.util.Collection;

public class PuzzleBobbleViewController extends StackPane implements Pausable {

    @FXML private AnchorPane gameOverScreen;
    @FXML private AnchorPane pauseScreen;
    @FXML private Line shooterLine;
    @FXML private Pane tilePane;
    @FXML private Label finalScore;
    @FXML private Label score;
    private static final double SHOOTER_LINE_LENGTH = 57.0;
    private static final double SHOOT_TILE_START_X = 310.0;
    private static final double SHOOT_TILE_START_Y = 566.0;
    private static final double MAX_ANGLE = -0.26179;
    private static final double MIN_ANGLE = -2.87979;
    private static final double NEXT_TILE_X = 134.0;
    private static final double NEXT_TILE_Y = 567.0;
    private final PuzzleBobbleBoard board;
    private final BooleanProperty turnOver;
    private final BooleanProperty gameOver;
    private PuzzleBobbleTile currentTile;
    private PuzzleBobbleTile nextTile;
    private double shooterAngle;
    private boolean isShooting;

    public PuzzleBobbleViewController(final PuzzleBobbleBoard board) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PuzzleBobbleView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.board = board;
        turnOver = new SimpleBooleanProperty(false);
        gameOver = new SimpleBooleanProperty(false);
    }

    public void initializeView() {
        initializeBoard();
        registerMouseListeners();
        initializeShooter();
    }

    public BooleanProperty isTurnOver() {
        return turnOver;
    }

    public BooleanProperty isGameOver() {
        return gameOver;
    }

    private void initializeBoard() {
        final Collection<PuzzleBobbleTile> tiles = board.getTiles();
        for (final PuzzleBobbleTile tile : tiles) {
            tilePane.getChildren().add(tile.getVisualTile());
            tile.setVisualX(board.getXTileCoordinates(tile.getX(), tile.getY()));
            tile.setVisualY(board.getYTileCoordinates(tile.getY()));
        }
    }

    private void initializeShooter() {
        currentTile = new PuzzleBobbleTile((int) (Math.random() * 7));
        nextTile = new PuzzleBobbleTile((int) (Math.random() * 7));
        tilePane.getChildren().addAll(currentTile.getVisualTile(), nextTile.getVisualTile());

        // Position of tiles must be set AFTER they are added to UI canvas
        currentTile.setVisualX(SHOOT_TILE_START_X);
        currentTile.setVisualY(SHOOT_TILE_START_Y);
        nextTile.setVisualX(NEXT_TILE_X);
        nextTile.setVisualY(NEXT_TILE_Y);
    }

    private void registerMouseListeners() {
        setOnMouseMoved(event -> {
            if (!tilePane.isDisabled()) {
                updateShooterAngle(event.getX(), event.getY());
                event.consume();
            }
        });

        setOnMouseClicked(event -> {
            if (!isShooting && !tilePane.isDisabled()) {
                final TileShootingAnimation shootingAnimation = new TileShootingAnimation(board, currentTile, shooterAngle);
                shootingAnimation.start();
                shootingAnimation.stoppedProperty().addListener((observable, oldValue, newValue) -> {
                    if (board.isGameOver()) {
                        gameOverScreen.setVisible(true);
                        tilePane.setDisable(true);
                        finalScore.setText("Final Score : " + (int) board.getScore());
                        gameOver.set(true);
                    }

                    isShooting = false;
                    swapTiles();
                    turnOver.set(true);
                });
                isShooting = true;
            }
        });
    }

    private void swapTiles() {
        currentTile = nextTile;
        currentTile.setVisualX(SHOOT_TILE_START_X);
        currentTile.setVisualY(SHOOT_TILE_START_Y);
        nextTile = new PuzzleBobbleTile((int) (Math.random() * 7));
        tilePane.getChildren().add(nextTile.getVisualTile());
        nextTile.setVisualX(NEXT_TILE_X);
        nextTile.setVisualY(NEXT_TILE_Y);
    }

    private void updateShooterAngle(final double mouseX, final double mouseY) {
        shooterAngle = Math.atan2(mouseY - shooterLine.getEndY(),
                                  mouseX - shooterLine.getEndX());
        shooterAngle = Math.min(shooterAngle, MAX_ANGLE);
        shooterAngle = Math.max(shooterAngle, MIN_ANGLE);

        final double x = Math.cos(shooterAngle) * SHOOTER_LINE_LENGTH;
        final double y = Math.sin(shooterAngle) * SHOOTER_LINE_LENGTH;
        shooterLine.setEndX(shooterLine.getStartX() + x);
        shooterLine.setEndY(shooterLine.getStartY() + y);
    }

    @Override
    public void pause() {
        tilePane.setDisable(true);
        pauseScreen.setVisible(true);
    }

    @Override
    public void resume() {
        tilePane.setDisable(false);
        pauseScreen.setVisible(false);
        turnOver.set(false);
    }
}
