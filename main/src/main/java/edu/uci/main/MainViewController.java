package edu.uci.main;

import edu.uci.bejeweled.Bejeweled;
import edu.uci.puzzlebobble.PuzzleBobble;
import edu.uci.tmge.MultiplayerGame;
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
    final Bejeweled bejeweled = new Bejeweled();
    bejeweled.launch();
  }

  public void playPuzzleBobble() {
    final MultiplayerGame game = new MultiplayerGame();
    game.addGame(new PuzzleBobble(1));
    game.addGame(new PuzzleBobble(2));
    game.launch();
  }

  public void viewPlayerProfiles() {

  }
}
