package edu.uci.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class NameEntryController extends VBox {

  @FXML private TextField playerOneName;
  @FXML private TextField playerTwoName;
  @FXML private Button startGame;

  public NameEntryController(final Consumer<List<String>> playerNameConsumer) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/NameEntryView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    startGame.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        playerNameConsumer.accept(List.of(playerOneName.getText(), playerTwoName.getText()));
      }
    });
  }
}
