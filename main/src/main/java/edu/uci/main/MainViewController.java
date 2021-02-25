package edu.uci.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainViewController extends AnchorPane {

  public MainViewController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  public void playBejeweled() {

  }

  public void playPuzzleBobble() {

  }

  public void viewPlayerProfiles() {

  }
}
