package edu.uci.main;

import edu.uci.bejeweled.BejeweledViewController;
import edu.uci.puzzlebobble.PuzzleBobbleViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

  // TODO replace with Game Launcher
  public void playBejeweled() {
    final Stage stage = new Stage();
    final Scene mainScene = new Scene(new BejeweledViewController());
    stage.setTitle("Bejeweled - Player 1");
    stage.setScene(mainScene);
    stage.setResizable(false);
    stage.show();
  }

  // TODO replace with Game Launcher
  public void playPuzzleBobble() {
    final Stage stage = new Stage();
    final Scene mainScene = new Scene(new PuzzleBobbleViewController());
    stage.setTitle("PuzzleBobble - Player 1");
    stage.setScene(mainScene);
    stage.setResizable(false);
    stage.show();
  }

  public void viewPlayerProfiles() {

  }
}
