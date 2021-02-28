package edu.uci.puzzlebobble;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.IOException;

public class PuzzleBobbleViewController extends StackPane {

    @FXML private Circle nextTile;
    @FXML private Circle currentTile;
    @FXML private Line shooterLine;
    @FXML private Label score;
    private static final double MAX_ANGLE = -0.26179;
    private static final double MIN_ANGLE = -2.87979;
    private static final double SHOOT_VELOCITY = 15.0;
    private final double SHOOTER_LINE_LENGTH;
    private final double TILE_START_X;
    private final double TILE_START_Y;
    private double shooterAngle;

    public PuzzleBobbleViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PuzzleBobbleView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        registerMouseListeners();
        TILE_START_X = currentTile.getLayoutX();
        TILE_START_Y = currentTile.getLayoutY();
        SHOOTER_LINE_LENGTH = Math.sqrt(Math.pow(shooterLine.getStartX() - shooterLine.getEndX(), 2.0) +
                                        Math.pow(shooterLine.getStartY() - shooterLine.getEndY(), 2.0));
    }

    private void registerMouseListeners() {
        setOnMouseMoved(event -> {
            updateShooterAngle(event.getX(), event.getY());
            event.consume();
        });

        setOnMouseClicked(event -> {

            final AnimationTimer shootingAnimation = new AnimationTimer() {

                private double angle = shooterAngle;
                private double deltaX = getDeltaX();
                private double deltaY = getDeltaY();

                @Override
                public void handle(long now) {
                    currentTile.setLayoutX(currentTile.getLayoutX() + deltaX);
                    currentTile.setLayoutY(currentTile.getLayoutY() + deltaY);

                    // TODO use board dimensions
                    if (currentTile.getLayoutX() - currentTile.getRadius() <= 0.0 ||
                        currentTile.getLayoutX() + currentTile.getRadius() >= 789.0)
                    {
                        angle = -(angle + Math.PI);
                        deltaX = getDeltaX();
                        deltaY = getDeltaY();
                        System.out.println(angle);
                    }

                    // TODO check for collisions with other tiles, not just top of board
                    if (currentTile.getLayoutY() - currentTile.getRadius() <= 0.0) {
                        currentTile.setLayoutX(TILE_START_X);
                        currentTile.setLayoutY(TILE_START_Y);
                        stop();
                    }
                }

                private double getDeltaX() {
                    return Math.cos(angle) * SHOOT_VELOCITY;
                }

                private double getDeltaY() {
                    return Math.sin(angle) * SHOOT_VELOCITY;
                }
            };

            shootingAnimation.start();
        });
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
}
