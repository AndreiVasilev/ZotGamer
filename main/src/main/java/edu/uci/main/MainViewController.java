package edu.uci.main;

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

  public void playBejeweled() {

  }

  public void playPuzzleBobble() {
    final Stage stage = new Stage();
    final Scene mainScene = new Scene(new PuzzleBobbleViewController());
    stage.setResizable(false);
    stage.setTitle("Zot Gamer");
    stage.setScene(mainScene);
    stage.show();
  }

  public void viewPlayerProfiles() {

  }
}
