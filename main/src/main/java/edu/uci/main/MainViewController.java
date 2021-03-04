package edu.uci.main;

import edu.uci.tmge.GameLauncher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
    launchGame(GameType.BEJEWELED);
  }

  public void playPuzzleBobble() {
    launchGame(GameType.PUZZLE_BOBBLE);
  }

  public void viewScoreboard() {
    final Stage stage = new Stage();
    stage.setResizable(false);
    stage.setScene(new Scene(new ScoreBoardController()));
    stage.show();
  }

  private void launchGame(final GameType gameType) {
    final Stage nameWindow = new Stage();
    final NameEntryController nameEntry = new NameEntryController(playerNames -> {
      gameLauncher.launchGame(gameType.name(), playerNames);
      nameWindow.close();
    });

    nameWindow.setScene(new Scene(nameEntry));
    nameWindow.setResizable(false);
    nameWindow.show();
  }
}
