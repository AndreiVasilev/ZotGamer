package edu.uci.main;

import edu.uci.tmge.GameLauncher;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class MainViewController extends AnchorPane {

  private final GameLauncher gameLauncher;

  public MainViewController(final GameLauncher gameLauncher) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    this.gameLauncher = gameLauncher;
  }

  public void playBejeweled() {
    gameLauncher.launchGame(GameType.BEJEWELED.name(), List.of("Player 1", "Player 2"));
  }

  public void playPuzzleBobble() {
    gameLauncher.launchGame(GameType.PUZZLE_BOBBLE.name(), List.of("Player 1", "Player 2"));
  }

  public void viewPlayerProfiles() {

  }
}
