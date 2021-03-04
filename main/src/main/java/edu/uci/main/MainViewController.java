package edu.uci.main;

import edu.uci.puzzlebobble.PuzzleBobbleFactory;
import edu.uci.puzzlebobble.PuzzleBobbleWindowFactory;
import edu.uci.tmge.GameLauncher;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

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
    final GameLauncher gameLauncher = new GameLauncher();
    gameLauncher.registerGame("Puzzle Bobble", new PuzzleBobbleFactory(), new PuzzleBobbleWindowFactory());
    gameLauncher.launchGame("Puzzle Bobble", List.of("Player 1", "Player 2"));
  }

  public void viewPlayerProfiles() {

  }
}
