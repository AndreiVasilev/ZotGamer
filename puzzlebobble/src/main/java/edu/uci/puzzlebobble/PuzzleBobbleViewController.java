package edu.uci.puzzlebobble;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    private final double SHOOTER_LINE_LENGTH;
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
        SHOOTER_LINE_LENGTH = Math.sqrt(Math.pow(shooterLine.getStartX() - shooterLine.getEndX(), 2.0) +
                                        Math.pow(shooterLine.getStartY() - shooterLine.getEndY(), 2.0));
    }

    private void registerMouseListeners() {
        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                shooterAngle = Math.atan2(event.getY() - shooterLine.getEndY(),
                                          event.getX() - shooterLine.getEndX());
                shooterAngle = Math.min(shooterAngle, MAX_ANGLE);
                shooterAngle = Math.max(shooterAngle, MIN_ANGLE);

                final double x = Math.cos(shooterAngle) * SHOOTER_LINE_LENGTH;
                final double y = Math.sin(shooterAngle) * SHOOTER_LINE_LENGTH;
                shooterLine.setEndX(shooterLine.getStartX() + x);
                shooterLine.setEndY(shooterLine.getStartY() + y);

                event.consume();
            }
        });
    }

}
