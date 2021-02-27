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
    }

    private void registerMouseListeners() {
        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                shooterLine.setStartX(event.getSceneX());
                shooterLine.setStartY(event.getSceneY());
                event.consume();
            }
        });
    }

}
